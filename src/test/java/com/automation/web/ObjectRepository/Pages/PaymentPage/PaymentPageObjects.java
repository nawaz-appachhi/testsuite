package com.automation.web.ObjectRepository.Pages.PaymentPage;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

import com.automation.core.web.GenericMethods;
import com.automation.web.ObjectRepository.Pages.AddressPage.AddressPageObjects;

public class PaymentPageObjects {

	/**
	 * @author 300019239 - Nitesh
	 *
	 */

	public WebElement element = null;
	public List<WebElement> elements;
	public String ordernumber;
	WebDriver driver;
	

	GenericMethods objGenericMethods;
	AddressPageObjects objAddressPageObjects;

	/**
	 * @param driver
	 */

	public PaymentPageObjects(WebDriver driver) {
		super();
		PageFactory.initElements(driver, this);
		objGenericMethods = new GenericMethods(driver);
		objAddressPageObjects = new AddressPageObjects(driver);
		this.driver = driver;
	}

	@FindBy(id = "tab_credit_card")
	public WebElement CreditDebitCard;

	@FindBy(id = "tab_net_banking")
	public WebElement NetBanking;

	@FindBy(id = "tab_cod")
	public WebElement CashOnDelivery;

	@FindBy(id = "cod-btn-confirm")
	public WebElement PayOnDelivery;

	@FindBy(xpath = "//div[@class='msg-success']")
	public WebElement OrderConfirmedTextForCashOnDelivery;

	/**
	 * Modified by :Anu
	 * xpath was wrong
	 */
	@FindBy(xpath = "//div[@class='msg-num']/strong")
	public WebElement OrderNumber;

	@FindBy(xpath = "//*[@class='track']/a")
	public WebElement ViewOrder;

	//	@FindBy(xpath = "//div[2]/span[2][@class='orderInfo-value']")
	//	public WebElement OrderNumberForCashOnDeliveryInOrdersAndReturns;

	@FindBy(id = "tab_phonepe")
	public WebElement PhonePe;

	@FindBy(id = "tab_gift_card")
	public WebElement GiftCards;

	@FindBy(id = "tab_emi")
	public WebElement EMIcreditCard;

	@FindBy(id = "tab_wallets")
	public WebElement Wallets;

	@FindBy(xpath = "//input[@id='card-number-txt']")
	public WebElement GiftCardNumber;

	@FindBy(xpath = "//input[@id='card-pin-txt']")
	public WebElement GiftCardPin;

	@FindBy(xpath = "//button[@id='apply-btn-confirm']")
	public WebElement ApplyGcBtn;

	@FindBy(xpath = "//div[@class='bank-name' and text()='HDFC']")
	public WebElement HdfcNetBanking;

	@FindBy(xpath = "//*[@class='tab-content']/form[2]/div[3]/button")
	public WebElement PayNowNetBanking;

	@FindBy(xpath = ".//span[text()='View ']")
	public WebElement ViewDetaillink;

	@FindBy(xpath = "//div[@class='wallets']/div[1]")
	public WebElement MobiKwick;

	/**
	 * Added by Aishu- credit debit payment
	 */
	@FindBy(name = "card_number")
	public WebElement CardNumTxtBx;

	public WebElement getCardNumTxtBx() {
		objGenericMethods.waitDriver(CardNumTxtBx, "CardNumberTextBox");
		return CardNumTxtBx;
	}

	public WebElement getCardHolderNameTxtBx() {
		objGenericMethods.CheckWebElementFound(CardHolderNameTxtBx, "CardHolderNameTxtBx");
		return CardHolderNameTxtBx;
	}

	public WebElement getCardValidityMonth() {
		objGenericMethods.CheckWebElementFound(CardValidityMonth, "CardValidityMonth");
		return CardValidityMonth;
	}

	public List<WebElement> getMonthList() {
		objGenericMethods.CheckWebElementsListFound(MonthList, "MonthList");
		return MonthList;
	}

	public WebElement getCardValidityYear() {
		objGenericMethods.CheckWebElementFound(CardValidityYear, "CardValidityYear");
		return CardValidityYear;
	}

	public List<WebElement> getYearList() {
		objGenericMethods.CheckWebElementsListFound(YearList, "YearList");
		return YearList;
	}

	public WebElement getCvvTxtBx() {
		objGenericMethods.CheckWebElementFound(CvvTxtBx, "CvvTxtBx");
		return CvvTxtBx;
	}

	public WebElement getCreditDebitPayNowBtn() {
		objGenericMethods.CheckWebElementFound(CreditDebitPayNowBtn, "CreditDebitPayNowBtn");
		return CreditDebitPayNowBtn;
	}

	public WebElement getSaveCardDetailsCheckBox() {
		objGenericMethods.CheckWebElementFound(saveCardDetails, "saveCardDetails");
		return saveCardDetails;
	}

	/**
	 * Added by Aishu- credit debit payment
	 */
	@FindBy(xpath = "//input[@placeholder='Name On Card']")
	public WebElement CardHolderNameTxtBx;

	/**
	 * Added by Aishu- credit debit payment
	 */
	@FindBy(name = "card_month")
	public WebElement CardValidityMonth;

	/**
	 * Added by Aishu- credit debit payment
	 */
	@FindAll({@FindBy(xpath = "//*[@name='card_month']/option")})
	public List<WebElement> MonthList;

	/**
	 * Added by Aishu- credit debit payment
	 */
	@FindBy(name = "card_year")
	public WebElement CardValidityYear;

	/**
	 * Added by Aishu- credit debit payment
	 */
	@FindBy(xpath = "//*[@name='card_year']/option")
	public List<WebElement> YearList;

	/**
	 * Added by Aishu- credit debit payment
	 */
	@FindBy(xpath = "//input[@placeholder='CVV']")
	public WebElement CvvTxtBx;

	/**
	 * Added by Aishu- credit debit payment
	 */
	@FindBy(xpath = "//*[@class='card-details']//button[@type='submit' and contains(@class, 'primary-btn')]")
	public WebElement CreditDebitPayNowBtn;

	@FindBy(xpath="//input[@type='checkbox']")
	public WebElement saveCardDetails;

	/**
	 * Modified by : Anu
	 * xpath was wrong for WalletPayNow
	 */
	@FindBy(xpath = "//form[@id='wallets']/div[3]/button[@type='submit']")
	public WebElement WalletPayNow;

	@FindBy(xpath = "//*[text()='Axis']")
	public WebElement AxisBank;

	@FindBy(xpath = "//div[@class='row order-summary']")
	public WebElement orderSummary;

	@FindBy(xpath = "//div[@class='address']")
	public WebElement addressSummary;

	@FindBy(xpath = "//div[@class='address-summary']")
	public WebElement SelectedAddress;

	//	@FindBy(xpath = "//span[@id='you-pay-amt']")
	//	public WebElement finalPayableAmount;

	@FindBy(id = "you-pay-amt")
	public WebElement finalPayableAmount;

	@FindBy(className = "summary-bd")
	public WebElement finalDeliveryAddress;

	@FindBy(xpath="//*[@class='trackOrder']")
	public WebElement ConfirmPageViewOrderLink;

	@FindBy (xpath = "(//span[@class='orderInfo-value'])[2]")
	public WebElement OrderNumberOnMyOrder;
	
	public WebElement getOrderNumberOnMyOrder() {
		objGenericMethods.waitDriver(OrderNumberOnMyOrder, "Order Number On My Order Page");
		objGenericMethods.CheckWebElementFound(OrderNumberOnMyOrder, "OrderNumberOnMyOrder");
		objGenericMethods.takeSnapShot();
		return OrderNumberOnMyOrder;
	}

	public WebElement getConfirmPageViewOrderLink() {
		return ConfirmPageViewOrderLink;
	}

	/**
	 * Created by : Anu
	 * Added locator for Continueshopping link
	 * Added locator for Order number from Orders and Returns page
	 */
	@FindBy(xpath="//a[text()='Continue Shopping ']")
	public WebElement ContinueShoppingLink;

	@FindAll(@FindBy(xpath="//span[@class='order-orderNumber']"))
	public List<WebElement> OrdernumberFromOrderandReturnsPage;

	public List<WebElement> getOrdernumberFromOrderandReturnsPage() {
		objGenericMethods.CheckWebElementsListFound(OrdernumberFromOrderandReturnsPage, "OrdernumberFromOrderandReturnsPage");
		return OrdernumberFromOrderandReturnsPage;
	}

	public WebElement getContinueShoppingLink() {
		objGenericMethods.CheckWebElementFound(ContinueShoppingLink, "ContinueShoppingLink");
		return ContinueShoppingLink;
	}

	public WebElement getAxisBank() {
		objGenericMethods.CheckWebElementFound(AxisBank, "AxisBank");
		return AxisBank;
	}

	public WebElement getCreditDebitCard() {
		objGenericMethods.CheckWebElementFound(CreditDebitCard, "CreditDebitCard");
		return CreditDebitCard;
	}

	/**
	 * Modified By: Aishurya - added waitDriver()
	 */
	public WebElement getNetBanking() {
		objGenericMethods.CheckWebElementFound(NetBanking, "NetBanking");
		objGenericMethods.waitDriver(NetBanking, "Net banking Tab");
		return NetBanking;
	}

	public WebElement getCashOnDelivery() {
		objGenericMethods.CheckWebElementFound(CashOnDelivery, "CashOnDelivery");
		return CashOnDelivery;
	}

	public WebElement getPayOnDelivery() {
		objGenericMethods.CheckWebElementFound(PayOnDelivery, "PayOnDelivery");
		return PayOnDelivery;
	}

	public WebElement getOrderConfirmedTextForCashOnDelivery() {
		objGenericMethods.CheckWebElementFound(OrderConfirmedTextForCashOnDelivery, "OrderConfirmedTextForCashOnDelivery");
		return OrderConfirmedTextForCashOnDelivery;
	}

	public WebElement getOrderNumber() {
		objGenericMethods.CheckWebElementFound(OrderNumber, "Order Number");
		return OrderNumber;
	}

	public WebElement getViewOrderForCashOnDelivery() {
		objGenericMethods.CheckWebElementFound(ViewOrder, "ViewOrderForCashOnDelivery");
		return ViewOrder;
	}

	//	public WebElement getOrderNumberForCashOnDeliveryInOrdersAndReturns() {
	//		objGenericMethods.CheckWebElementFound(OrderNumberForCashOnDeliveryInOrdersAndReturns, "OrderNumberForCashOnDeliveryInOrdersAndReturns");
	//		return OrderNumberForCashOnDeliveryInOrdersAndReturns;
	//	}

	public WebElement getPhonePe() {
		objGenericMethods.CheckWebElementFound(PhonePe, "PhonePe");
		return PhonePe;
	}

	public WebElement getEMIcreditCard() {
		objGenericMethods.CheckWebElementFound(EMIcreditCard, "EMIcreditCard");
		return EMIcreditCard;
	}

	public WebElement getGiftCards() {
		objGenericMethods.CheckWebElementFound(GiftCards, "GiftCards");
		return GiftCards;
	}

	public WebElement getWallets() {
		objGenericMethods.CheckWebElementFound(Wallets, "Wallets");
		return Wallets;
	}

	public WebElement getPayNowNetBanking() {
		objGenericMethods.CheckWebElementFound(PayNowNetBanking, "PayNowNetBanking");
		return PayNowNetBanking;
	}

	public WebElement getHdfcNetBanking() {
		objGenericMethods.CheckWebElementFound(HdfcNetBanking, "HdfcNetBanking");
		return HdfcNetBanking;
	}

	public WebElement getGiftCardNumber() {
		objGenericMethods.CheckWebElementFound(GiftCardNumber, "GiftCardNumber");
		return GiftCardNumber;
	}

	public WebElement getGiftCardPin() {
		objGenericMethods.CheckWebElementFound(GiftCardPin, "GiftCardPin");
		return GiftCardPin;
	}

	public WebElement getApplyGcBtn() {
		objGenericMethods.CheckWebElementFound(ApplyGcBtn, "ApplyGcBtn");
		return ApplyGcBtn;
	}

	public WebElement getViewDetaillink() {
		objGenericMethods.CheckWebElementFound(ViewDetaillink, "ViewDetaillink");
		return ViewDetaillink;
	}

	public WebElement getMobiKwick() {
		objGenericMethods.CheckWebElementFound(MobiKwick, "MobiKwick");
		return MobiKwick;
	}

	public WebElement getWalletPayNow() {
		objGenericMethods.CheckWebElementFound(WalletPayNow, "WalletPayNow");
		return WalletPayNow;
	}

	public WebElement getOrderSummary() {
		objGenericMethods.CheckWebElementFound(orderSummary, "orderSummary");
		return orderSummary;
	}

	public WebElement getAddressSummary() {
		objGenericMethods.CheckWebElementFound(addressSummary, "addressSummary");
		return addressSummary;
	}

	public WebElement getSelectedAddress() {
		objGenericMethods.CheckWebElementFound(SelectedAddress, "SelectedAddress");
		return SelectedAddress;
	}

	public WebElement getFinalPayableAmount() {
		objGenericMethods.CheckWebElementFound(finalPayableAmount, "finalPayableAmount");
		return finalPayableAmount;
	}

	/**
	 * 
	 * ModifiedBy : Neeraj
	 * Description: Removed Thread.sleep and added WaitDriverWhenReady()
	 */
	public void SelectCreditDebitCard() {
		objGenericMethods.takeSnapShot();
		objGenericMethods.waitDriverWhenReady(getCreditDebitCard(), "Credit Debit Card");
		getCreditDebitCard().click();
		objGenericMethods.ReportClickEvent("Credit Debit Card");
	}

	public void SelectNetBanking() {
		objGenericMethods.takeSnapShot();
		getNetBanking().click();
		objGenericMethods.ReportClickEvent("NetBanking");
	}

	public void SelectCashOnDelivery() {
		objGenericMethods.takeSnapShot();
		getCashOnDelivery().click();
		objGenericMethods.ReportClickEvent("Cash On Delivery");
	}

	/**
	 * Name: Irfan
	 * Description: This method will help user to click on "Pay On Delivery" button (COD)
	 * 
	 * Modified by: Nitesh- added try catch block
	 */

	public void ClickCODPayOnDeliveryBtn() {
		try {
			objGenericMethods.takeSnapShot();
			getPayOnDelivery().click();
			objGenericMethods.ReportClickEvent("Pay On Delivery");
			Reporter.log("Passed: Product selected is available for COD!");
		} catch (Exception e) {
			Reporter.log("Failed: Product selected is not available for COD!");
		}
	}

	/**
	 * Name: Irfan
	 * Description: This method will help user to get Order Confirmation Text (COD)
	 */
	public void VerifyCODOrderConfirmedTxt() {
		objGenericMethods.takeSnapShot();
		getOrderConfirmedTextForCashOnDelivery().getText();
		objGenericMethods.ReportClickEvent("Order Confirmed Text For COD");
	}

	/**
	 * Name: Irfan
	 * Description: This method will help user to get Order Number (COD)
	 * Modified By:Aishurya: Method is common for all type of payment
	 */
	public void VerifyOrderNumber() {
		objGenericMethods.waitDriverWhenReady(getOrderNumber(), "Order number");
		ordernumber=getOrderNumber().getText();
		System.out.println("Order Number on Order Confirmation Page: " + ordernumber);
		Reporter.log("Order Number on Order Confirmation Page: " + ordernumber);
	}

	/**
	 * Name: Irfan
	 * Description: This method will help user to get View Order (COD)
	 */
	public void ClickCODViewOrderBtn() {
		objGenericMethods.takeSnapShot();
		getViewOrderForCashOnDelivery().click();
		objGenericMethods.ReportClickEvent("View Order For COD");
	}

	/**
	 * Name: Irfan
	 * Description: This method will help user to View Order Number In "Orders & Returns Page" (COD)
	 */
	//	public void VerifyCODOrderNumberInOrdersAndReturnsPage() {
	//		objGenericMethods.takeSnapShot();
	//		getOrderNumberForCashOnDeliveryInOrdersAndReturns().getText();
	//		objGenericMethods.ReportClickEvent("View Order Number In Orders & Returns Page");
	//	}

	public void SelectEMIcreditCard() throws InterruptedException{
		objGenericMethods.takeSnapShot();
		getEMIcreditCard().click();
		objGenericMethods.ReportClickEvent("EMI Credit Card");
	}

	public void SelectPhonePe() {
		objGenericMethods.takeSnapShot();
		getPhonePe().click();
		objGenericMethods.ReportClickEvent("PhonePe");
	}

	/**
	 * Modified by:Aishurya
	 * Reason: Added waitDriver() instead of thread sleep
	 */
	public void SelectGiftCards() {
		objGenericMethods.takeSnapShot();
		objGenericMethods.waitDriver(getGiftCards(), "Gifit Card payment Tab");
		getGiftCards().click();
		objGenericMethods.ReportClickEvent("Gift Cards");
	}

	public void SelectWallets() {
		objGenericMethods.takeSnapShot();
		getWallets().click();
		objGenericMethods.ReportClickEvent("Wallets");
	}

	/**
	 * Modified by:Aishurya
	 * Reason: Added waitDriver() instead of thread sleep
	 */

	/**
	 * Modified Nitesh
	 * Reason: Removed thread.sleep and parameterised the method
	 */
	public void EnterGiftCardData(String GiftCardNumber, String GiftCardPin) {
		objGenericMethods.waitDriver(getGiftCardNumber(), "Gift card number field");
		getGiftCardNumber().sendKeys(GiftCardNumber);
		objGenericMethods.waitDriver(getGiftCardPin(), "Gift card pin field");
		getGiftCardPin().sendKeys("GiftCardPin");
		objGenericMethods.takeSnapShot();
		getApplyGcBtn().click();
		objGenericMethods.ReportClickEvent("Apply Gift Card Button");
	}

	public void ClickOnViewDetailLink() {
		objGenericMethods.takeSnapShot();
		getViewDetaillink().click();
		objGenericMethods.ReportClickEvent("View Detail link");
	}

	/**
	 * 
	 * ModifiedBy : Neeraj
	 * Description: Removed Thread.sleep and added WaitDriverWhenReady()
	 */
	public void EnterNetBankingData() {
		objGenericMethods.takeSnapShot();
		getHdfcNetBanking().click();
		objGenericMethods.ReportClickEvent("NetBanking");
		objGenericMethods.takeSnapShot();
		getPayNowNetBanking().click();
		objGenericMethods.ReportClickEvent("NetBanking PayNow Button");
	}

	public void ClickOnWalletPayNow() {
		objGenericMethods.takeSnapShot();
		getWalletPayNow().click();
		objGenericMethods.ReportClickEvent("Continue To Payment Button");
	}

	public void ClickOnMobikwik() {
		objGenericMethods.takeSnapShot();
		getMobiKwick().click();
		objGenericMethods.ReportClickEvent("Wallet PayNow Button");
	}

	public void ClickOnAxisBank(){
		objGenericMethods.takeSnapShot();
		getAxisBank().click();
		objGenericMethods.ReportClickEvent("Axis Bank Button");
	}

	/**
	 * 
	 * ModifiedBy : Neeraj
	 * Description: Removed Thread.sleep and added WaitDriverWhenReady()
	 */
	public void VerifyOrderSumamry()	{
		objGenericMethods.waitDriverWhenReady(getOrderSummary(), "Order Summary");
		objGenericMethods.VerifyTwoString(getOrderSummary(), "Order Summary");
	}

	public void VerifyDeliveryAddress(){
		objGenericMethods.VerifyTwoString(getAddressSummary(), "3rd Floor, A Block,AKR Tech Park, 7th Mile");
	}

	/**
	 * Modified by: Nitesh
	 * Reason: it used to verify total payable text and not amount, so changed it to verify amount is displayed and not empty
	 */
	public void VerifyFinalPayableAmount(){
		objGenericMethods.VerifyStringShouldNotEmpty(getFinalPayableAmount(), "Total Payable");
	}

	public void VerifyOrderConfirmedTitleForCOD()
	{
		objGenericMethods.VerifyStringShouldNotEmpty(getOrderConfirmedTextForCashOnDelivery(), "Order Confirmed");
	}

	public void OrderNumberForCOD()
	{
		objGenericMethods.VerifyStringShouldNotEmpty(getOrderNumber(), "Order Number");
	}

	//	public void VerifyOrderNumberForCODInOrdersAndReturnsPage()
	//	{
	//		objGenericMethods.VerifyStringShouldNotEmpty(getOrderNumberForCashOnDeliveryInOrdersAndReturns(), "Order Number");
	//	}

	/**
	 * Created by : Anu
	 * Added method to click Continue shopping link
	 * 
	 * Modified by : Anu
	 * Modified VerifyOrdernumberforCOD()
	 */
	public void ClickOnContinueShoppingLink()
	{
		objGenericMethods.takeSnapShot();
		getContinueShoppingLink().click();
		objGenericMethods.ReportClickEvent("Continue shopping link");
	}

	/**
	 *  Modified By: Neeraj
	 *  Reason : added takeSnapShot() and ReportClickEvent()
	 *  Modified By:Aishurya: Method is common for all type of payment
	 *  
	 *  Modified by : Nitesh
	 *  Modified to verify order number only
	 */
	
	public void VerifyOrdernumber()	{
		String ActualOrdernumber = getOrderNumberOnMyOrder().getText();
		System.out.println("Order Number on Orders page: " + ActualOrdernumber);
		Reporter.log("Order Number on Orders page: " + ActualOrdernumber);
		if(ActualOrdernumber.equalsIgnoreCase(ordernumber)){
			Reporter.log("Passed : Order number is matched!");
			System.out.println("Passed : Order number is matched!");
		}else{
			Reporter.log("Failed : Order number is not matched or is not displayed!");
			System.out.println("Failed : Order number is not matched or is not displayed!");
		}
	}
	
	/**
	 * Added by: Aishurya: Payment using valid card details
	 */

	/**
	 * Modified by: Nitesh: Removed thread.sleep from the method, and parameterized it to get data from test data file
	 */
	/**
	 * Modified By: Aishu: Added takeSnapShot()
	 */
	public void CreditDebitPaymentComplete(String CardNumber, String CardHolderName, String CVVnumber)
	{
		getCardNumTxtBx().sendKeys(CardNumber);
		getCardHolderNameTxtBx().clear();
		getCardHolderNameTxtBx().sendKeys(CardHolderName);
		getCardValidityMonth().click();
		getMonthList().get(1).click();
		getCardValidityYear().click();
		getYearList().get(1).click();
		getCvvTxtBx().clear();
		getCvvTxtBx().sendKeys(CVVnumber);
		//		getSaveCardDetailsCheckBox().click();
		objGenericMethods.takeSnapShot();
		getCreditDebitPayNowBtn().click();
		objGenericMethods.takeSnapShot();
	}

	/**
	 * Addded By Aishurya: Click on View details link on payment confirmation page
	 */
	public void GoToOrderDetailsPage() {
		objGenericMethods.waitDriverWhenReady(getConfirmPageViewOrderLink(), "View order link");
		objGenericMethods.takeSnapShot();
		getConfirmPageViewOrderLink().click();
		objGenericMethods.ReportClickEvent("View Order on Order Confirmation Page");
		
	}

	/**
	 * Created By : Neeraj
	 * Description: Method to click on NetBanking Tab
	 */
	public void ClickOnNetBankingTab()
	{
		objGenericMethods.takeSnapShot();
		getNetBanking().click();
		objGenericMethods.ReportClickEvent("NetBanking Tab");

	}

}