package com.automation.web.ObjectRepository.Pages.AddressPage;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

import com.automation.core.web.GenericMethods;
import com.automation.web.ObjectRepository.Pages.Header.HeaderPageObject;

public class AddressPageObjects{

	/**
	 * @author 300019239 - Nitesh
	 *
	 */

	public WebElement element = null;

	public List<WebElement> elements ;
	GenericMethods objGenericMethods;
	HeaderPageObject objHeaderPageObject;
	ProfileAddressPage objProfileAddressPage;
	WebDriver driver;

	public AddressPageObjects(WebDriver driver) {
		super();
		PageFactory.initElements(driver, this);
		objGenericMethods = new GenericMethods(driver);
		objHeaderPageObject=new HeaderPageObject(driver);
		objProfileAddressPage=new ProfileAddressPage(driver);
		this.driver = driver;
	}

	@FindBy(xpath = "//*[@class='list-add-address']/div")
	public WebElement AddNewAddress;

	@FindBy(xpath = ".//button[text()='Edit']")
	public WebElement EditAddress;

	@FindAll({ @FindBy(xpath = "//button[text()='Remove']") })
	public List<WebElement> RemoveAddress;

	/**
	 * Modified by Aishu: wrong locator
	 */
	@FindBy(xpath = "//button[text()='CONTINUE TO PAYMENT']")
	public WebElement ContinueToPaymentButton;

	@FindBy(id = "pincode")
	public WebElement Pincode;

	@FindBy(id = "locality")
	public WebElement Locality;

	@FindBy(id = "city")
	public WebElement City;

	@FindBy(id = "state")
	public WebElement State;

	@FindBy(id = "name")
	public WebElement Name;

	@FindBy(id = "address")
	public WebElement Address;

	@FindBy(id = "mobile")
	public WebElement MobileNo;

	@FindBy(id = "home")
	public WebElement HomeAddressType;

	@FindBy(id = "ofc")
	public WebElement OfficeRadioBtn;

	@FindBy(xpath = "//*[@class='button-sticky-buttom']/button")
	public WebElement Continue;

	@FindBy(xpath = "//button[@class='green-button submit clickable' and @type='button']")
	public WebElement SaveButton;

	@FindBy(xpath="//div[@class='white-row buttons']/button[1]")
	public WebElement CancelButton;

	@FindBy(xpath="//span[@class='expand payment-hide']")
	public WebElement ViewDetails;
	
	@FindBy(xpath="//*[@class='left']/.//div[@class='address']")
	public WebElement deliveryAddressDetail;
	/**
	 * order total
	 * old xpath="(//span[@class='value'])[5]"
	 * Modified by -Shivaprasad
	 * new xpath-".//*[@class='sub-total large']/span[contains(text(),'Rs.')]"
	 */
	@FindBy(xpath=".//*[@class='sub-total large']/span[contains(text(),'Rs.')]")
	public WebElement orderTotal;
	
	@FindBy(xpath = "//span[@id='you-pay-amt']")
	public WebElement payableAmount;
	
	@FindBy(xpath="//*[@id='city' and contains(@class, 'enabled')]")
	public WebElement cityDropDownEnabled;
	
	public WebElement getCityDropDownEnabled() {
		objGenericMethods.CheckWebElementFound(cityDropDownEnabled, "cityDropDownEnabled");
		return cityDropDownEnabled;
	}

	public WebElement getContinue() {
		objGenericMethods.CheckWebElementFound(Continue, "Continue");
		return Continue;
	}

	public WebElement getHomeAddressType() {
		objGenericMethods.CheckWebElementFound(HomeAddressType, "HomeAddressType");
		return HomeAddressType;
	}

	public WebElement getViewDetails() {
		objGenericMethods.CheckWebElementFound(ViewDetails, "ViewDetails");
		return ViewDetails;
	}

	public WebElement getAddNewAddress() {
		objGenericMethods.CheckWebElementFound(AddNewAddress, "AddNewAddress");
		return AddNewAddress;
	}

	public WebElement getEditAddress() {
		objGenericMethods.CheckWebElementFound(EditAddress, "EditAddress");
		return EditAddress;
	}

	public List<WebElement> getRemoveAddress() {
		objGenericMethods.CheckWebElementsListFound(RemoveAddress, "RemoveAddress");
		return RemoveAddress;
	}

	public WebElement getContinueToPaymentButton() {
		objGenericMethods.CheckWebElementFound(ContinueToPaymentButton, "ContinueToPaymentButton");
		return ContinueToPaymentButton;
	}

	public WebElement getOfficeRadioButton() {
		objGenericMethods.CheckWebElementFound(OfficeRadioBtn, "OfficeRadioBtn");
		return OfficeRadioBtn;
	}

	public WebElement getPincode() {
		objGenericMethods.CheckWebElementFound(Pincode, "Pincode");
		return Pincode;
	}

	public WebElement getLocality() {
		objGenericMethods.CheckWebElementFound(Locality, "Locality");
		return Locality;
	}

	public WebElement getCity() {
		objGenericMethods.CheckWebElementFound(City, "City");
		return City;
	}

	public WebElement getState() {
		objGenericMethods.CheckWebElementFound(State, "State");
		return State;
	}

	public WebElement getName() {
		objGenericMethods.CheckWebElementFound(Name, "Name");
		return Name;
	}

	public WebElement getMobileNo() {
		objGenericMethods.CheckWebElementFound(MobileNo, "MobileNo");
		return MobileNo;
	}

	public WebElement getAddress() {
		objGenericMethods.CheckWebElementFound(Address, "Address");
		return Address;
	}

	public WebElement getSaveButton() {
		objGenericMethods.CheckWebElementFound(SaveButton, "SaveButton");
		return SaveButton;
	}

	public WebElement getCancelButton() {
		objGenericMethods.CheckWebElementFound(CancelButton, "CancelButton");
		return CancelButton;
	}
	
	public WebElement getDeliveryAddressDetail() {
		objGenericMethods.CheckWebElementFound(deliveryAddressDetail, "deliveryAddressDetail");
		return deliveryAddressDetail;
	}
	
	/**
	 * @return
	 * @throws InterruptedException
	 * @author Neeraj
	 * Description: Removed Thread.sleep and added WaitDriverWhenReady()
	 */
	public WebElement getPayableAmount() {
		objGenericMethods.waitDriverWhenReady(payableAmount, "payableAmount");
		objGenericMethods.CheckWebElementFound(payableAmount, "payableAmount");
		return payableAmount;
	}
	
	/**
	 * @return
	 * @throws InterruptedException
	 * @author Neeraj
	 * Description: Removed Thread.sleep and added WaitDriverWhenReady()
	 */
	public WebElement getorderTotal() {
		objGenericMethods.waitDriverWhenReady(orderTotal, "orderTotal");
		objGenericMethods.CheckWebElementFound(orderTotal, "orderTotal");
		return orderTotal;
	}

	public void clickToAddNewAddress() {
		objGenericMethods.takeSnapShot();
		getAddNewAddress().click();
		objGenericMethods.ReportClickEvent("AddNewAddress");
	}

	/**
	 * Modified By:Aishurya
	 * Reason:Added explicit wait: waitDriver(), instead of thread sleep
	 */
	public void clickToEditAddress() {
		objGenericMethods.takeSnapShot();
		objGenericMethods.waitDriver(getEditAddress(), "Edit address button");
		getEditAddress().click();
		objGenericMethods.ReportClickEvent("EditAddress");
	}

	public void clickToRemoveAddress() {
		objGenericMethods.takeSnapShot();
		try {
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			driver.findElement(By.xpath("//div[@class='myntraStyleContinue']"));
		} catch (Exception e) {
		}
		getRemoveAddress().get(0).click();
		objGenericMethods.ReportClickEvent("RemoveAddress");
	}

	public void clickToProceedforPayment() {
		objGenericMethods.takeSnapShot();
		getContinueToPaymentButton().click();
		objGenericMethods.ReportClickEvent("Continue To Payment Button");
	}

	/**
	 * @param PINCODE
	 * @param userName
	 * @param mobileNo
	 * @param userAddress
	 * @param localityArea
	 * @throws InterruptedException
	 * Modified By : Neeraj
	 * Reason: Added click() for pincode to set focut on pincode text
	 * Modified By:Aishurya
	 * Reason:Added explicit wait:waitDriver() instead of thread sleep
	 * 
	 * Modified Nitesh
	 * Reason: removed thread.sleep and added dummy click event to wait for state and city data to get populated
	 */
	public void AddAddress(String PINCODE, String userName, String mobileNo, String userAddress, String localityArea)	{
		objGenericMethods.waitDriver(getPincode(), "Pincode Field");
		getPincode().click();
		getPincode().sendKeys(PINCODE);
		getName().sendKeys(userName);
		getMobileNo().sendKeys(mobileNo);
		getAddress().sendKeys(userAddress);
		try {
			objGenericMethods.waitDriver(getCityDropDownEnabled(), "City DropDown");
		} catch (Exception e) {
			// dummy object click event created to remove thread.sleep since it takes time to populate city and state
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			driver.findElement(By.xpath("//input[@id='state-2-2']")).click();
		}
		getLocality().sendKeys(localityArea);
	}

	/**
	 * @return
	 * @throws InterruptedException
	 * @author Neeraj
	 * Description: Removed Thread.sleep and added WaitDriverWhenReady()
	 */
	public void SelectHomeAddressType() {
		objGenericMethods.waitDriverWhenReady(getHomeAddressType(), "Home Address Type Radio button");
		objGenericMethods.takeSnapShot();
		getHomeAddressType().click();
		objGenericMethods.ReportClickEvent("Home Address Type Radio Button");
	}

	public void SelectOfficeAddressType() {
		objGenericMethods.takeSnapShot();
		getOfficeRadioButton().click();
		objGenericMethods.ReportClickEvent("Office Address Type Radio Button");
	}

	/**
	 * @return
	 * Modified By:Aishury- Added waitDriver()
	 * @throws InterruptedException
	 * Modify By:  Neeraj
	 * Description: Removed Thread.sleep and added WaitDriverWhenReady()
	 */
	public void clickToSaveAddress() {
		objGenericMethods.takeSnapShot();
		objGenericMethods.waitDriver(getSaveButton(), "Save button");
		getSaveButton().click();
		objGenericMethods.ReportClickEvent("Address Save Button");
	}

	public void clickToCancel() {
		objGenericMethods.takeSnapShot();
		getCancelButton().click();
		objGenericMethods.ReportClickEvent("Address Cancel Button");
	}

	/**
	 * Modified By: Neeraj
	 * Reason: There are two continue button to handle kept try catch block
	 * Modified By Aishu: added wait
	 * Modified by: Nitesh
	 * Reason: added dummy try catch to wait for edit address pop-up to go off for continue button to be clickable
	 */
	public void ClickToContinue() {
		objGenericMethods.scrollPage0to250();
		objGenericMethods.takeSnapShot();
		try {
			try {
				driver.findElement(By.xpath("//div[@class='myntraStyleContinue']"));
			} catch (Exception e) {
			}
			objGenericMethods.waitDriverWhenReady(Continue, "ContinueBtn");
			getContinue().click();
			objGenericMethods.ReportClickEvent("Continue Button");
		}catch(Exception e)
		{
			objGenericMethods.waitDriverWhenReady(ContinueToPaymentButton, "ContinueToPaymentButton");
			getContinueToPaymentButton().click();
			objGenericMethods.ReportClickEvent("Continue To Pay Button");
		}
	}

	/**
	 * Modified By:Aishurya-Added waitDriver()
	 */
	public void EditNameTextField(String name) {
		objGenericMethods.waitDriver(getName(), "Name Field");
		getName().clear();
		getName().sendKeys(name);
	}

	/**
	 * 
	 * ModifiedBy : Neeraj
	 * Description: Removed Thread.sleep and added WaitDriverWhenReady() and added Navigate back method
	 * 
	 * ModifiedBy : Nitesh
	 * Description: Added try catch block for click on Myntra logo and navigate back
	 *  
	 */
	public void resetAddress()	{
		// Empty Address Page
		objGenericMethods.HoverOnWebElement(objHeaderPageObject.getUserIcon());
		objGenericMethods.waitDriverWhenReady(objHeaderPageObject.getSavedAddressesLink(), "Save Address Link");
		objHeaderPageObject.ClickOnSavedAddressesLink();

		try {
			objProfileAddressPage.getNoSavedAddress().getText();
			Reporter.log("No saved address is present to be removed!");
			try {
				objProfileAddressPage.ClickMyntraLogoFromAddresspage();
			} catch (Exception e) {
				driver.navigate().back();
			}
		} catch (Exception e) {
			Reporter.log("Saved Addresses are present to be removed!");
			objProfileAddressPage.RemoveAllSavedAddressesFromAddressPage();
			objGenericMethods.waitDriverWhenReady(objProfileAddressPage.getMyntraLogo(), "Myntra Logo");
			try {
				objProfileAddressPage.getNoSavedAddress().getText();
				Reporter.log("Passed : Addresses save are removed successfully!");
				objProfileAddressPage.ClickMyntraLogoFromAddresspage();
			} catch (Exception e1) {
				objGenericMethods.takeSnapShot();
				driver.navigate().back();
			}
		}
	}

	/**
	 * Modified By : Anu
	 * Modified ClickAddNewAddress() with try-catch block
	 */
	public void ClickAddNewAddress() {
		
			try {
				objGenericMethods.takeSnapShot();
				getAddNewAddress().click();
				objGenericMethods.ReportClickEvent("Add New Address Link");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("Existing address is not available");
			}
		
	}

	/**
	 * 
	 * ModifiedBy : Neeraj
	 * Description: added WaitDriverWhenReady()
	 */
	public void ClickViewDetails(){
		objGenericMethods.takeSnapShot();
		objGenericMethods.waitDriverWhenReady(getViewDetails(), "View Details Link");
		getViewDetails().click();
		objGenericMethods.ReportClickEvent("ViewDetails");
	}
	
	/**
	 * 
	 * ModifiedBy : Nitesh
	 * Description: modified it to assert price, initially it was verifying text
	 */
	
	public void VerifyPriceDetails () {
		objGenericMethods.waitDriverWhenReady(getViewDetails(), "View Details");
		ClickViewDetails();
		objGenericMethods.waitDriverWhenReady(getorderTotal(), "Order Total");
		String Expected = getorderTotal().getText();
		String Actual = getPayableAmount().getText();
		System.out.println("Expected data is: "+ Expected);
		System.out.println("Actual data is: "+ Actual);
		String[] Expectedfinal = Expected.split(" ");
		String[] Actualfinal = Actual.split(" ");
		objGenericMethods.verifyTwoStringData(Actualfinal[1], Expectedfinal[1]);
	}
	
	/**
	 * Added By - Nitesh
	 * Method to verify whether new address is added or not
	 */
	
	public void VerifyAddressAdded()	{
		if (getEditAddress() != null)
		{
			Reporter.log("Passed : New Address is added successfully!");
		}
		else
		{
			Reporter.log("Failed : New Address is not added successfully!");
		}
	}

}