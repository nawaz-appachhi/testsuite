package com.automation.mobile.iOS.MobileWeb.ObjectRepository.PaymentObjects;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.automation.core.mobile.iOS.iOSGenericMethods;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class PaymentPageObjects {

	public IOSDriver<IOSElement> iDriver;
	iOSGenericMethods objiOSGenericMethods;

	public PaymentPageObjects(IOSDriver<IOSElement> iDriver) {
		PageFactory.initElements(new AppiumFieldDecorator(iDriver), this);
		objiOSGenericMethods = new iOSGenericMethods(iDriver);
	}

	/*
	 * Giftcards options 300021281
	 */
	@FindBy(xpath = ".//li[@id='tab_gift_card']")
	public IOSElement giftCards;

	@FindBy(xpath = ".//input[@id='card-number-txt']")
	public IOSElement giftCardNumber;

	@FindBy(xpath = ".//input[@id='card-pin-txt']")
	public IOSElement giftCardpin;

	@FindBy(xpath = ".//button[@id='apply-btn-confirm']")
	public IOSElement giftCardApplyButton;

	@FindBy(xpath = ".//div[@class='change-pay-mode']")
	public IOSElement changePaymentMode;
	
	
	/**
	 * Modified by subhasis
	 * className = "txt"
	 */
	
	@FindBy(xpath = "//div[text()='Payment']")
	public IOSElement paymentPageTitle;
	
	@FindBy(xpath = "//li[@id='tab_emi']")
	public IOSElement emiCreditCard;
	
	@FindBy(xpath = "//div[@class='emi']")
	public IOSElement emiSelectBanKDrpDwn;
	
	@FindBy(xpath = "//option[@value='20']")
	public IOSElement emiBankSelect;
	
	@FindBy(xpath = "//select[@id='EMIDurationList_20']")
	public IOSElement selectEMIdurationDrpDwn;
	
	@FindBy(xpath = "//option[@value='EMI03']")
	public IOSElement selectEMIduration;

	/*
	 * Giftcards options 300021281
	 */

	@FindBy(xpath = "//li[@id='tab_net_banking']")
	public IOSElement netBanking;

	/*
	 *  @monika
	 * changed method name,getter name
	 * Credit Card/Debit Card
	 * Modified By subhasis 
	 * tab_credit_card
	 */
	

	@FindBy(id = "tab_credit_card")
	public IOSElement CCandDC;

	/*
	 * Couopn
	 */

	@FindBy(xpath = "//input[@class='enter-coupon m-textbox']")
	public IOSElement EnterCouponCode;

	@FindBy(xpath = ".//*[text()='Apply']")
	public IOSElement ApplyCouponCode;

	public IOSElement getEnterCouponCode() {
		return EnterCouponCode;
	}

	public IOSElement getApplyCouponCode() {
		return ApplyCouponCode;
	}

	/*
	 * PhonePe
	 */
	@FindBy(xpath = "//li[@id='tab_phonepe']")
	public IOSElement phonepe;

	/*
	 * COD
	 */
	@FindBy(xpath = "//li[@id='tab_cod']")
	public IOSElement COD;

	/*
	 * Wallets
	 */
	@FindBy(xpath = "//li[@id='tab_wallets']")
	public IOSElement Wallets;
	
	@FindBy(xpath="//div[@class='bank-list']/div[1]")
	public IOSElement firstNetBankingOption;
	
	@FindBy(xpath="//button[@class='btn primary-btn btn-continue green-button clickable undefined mobile']")
	public IOSElement netbankingPayNowButton;
	
	/* Credit card Details*/
	
	@FindBy(className = "card-num-input")
	public IOSElement creditCardNumber;
	
	@FindBy(xpath = "//input[@placeholder='Name On Card']")
	public IOSElement nameOnCreditCard;
	
	@FindBy(xpath = "//select[@name='card_month']")
	public IOSElement expiryMonthField;
	
	@FindBy (xpath = "//option[@value='01']")
	public IOSElement expiryMonth;
	
	@FindBy (xpath = "//select[@name='card_year']")
	public IOSElement expiryYearField;
	
	@FindBy(xpath = "//option[@value='18']")
	public IOSElement expiryYear;
	
	@FindBy(xpath = "//input[@placeholder='CVV']")
	public IOSElement cvvField;
	
	@FindBy(className="summary-bd")
	public IOSElement deliveryAddress;
	
	public IOSElement getEMIcreditCard() {
		objiOSGenericMethods.CheckIOSElementFound(emiCreditCard, "emiCreditCard");
		return emiCreditCard;
	}
	
	public IOSElement getEMIselectBanKDrpDwn() {
		objiOSGenericMethods.CheckIOSElementFound(emiSelectBanKDrpDwn, "emiSelectBanKDrpDwn");
		return emiSelectBanKDrpDwn;
	}
	
	public IOSElement getEMIbankSelect() {
		objiOSGenericMethods.CheckIOSElementFound(emiBankSelect, "emiBankSelect");
		return emiBankSelect;
	}
	
	public IOSElement getSelectEMIdurationDrpDwn() {
		objiOSGenericMethods.CheckIOSElementFound(selectEMIdurationDrpDwn, "selectEMIdurationDrpDwn");
		return selectEMIdurationDrpDwn;
	}
	
	public IOSElement getSelectEMIduration() {
		objiOSGenericMethods.CheckIOSElementFound(selectEMIduration, "selectEMIduration");
		return selectEMIduration;
	}
	
	public IOSElement getdDeliveryAddress() {
		objiOSGenericMethods.CheckIOSElementFound(deliveryAddress, "deliveryAddress");
		return deliveryAddress;
	}
	
	public IOSElement getCreditCardNumber() {
		objiOSGenericMethods.CheckIOSElementFound(creditCardNumber, "creditCardNumber");
		return creditCardNumber;
	}
	
	public IOSElement getNameOnCreditCard() {
		objiOSGenericMethods.CheckIOSElementFound(nameOnCreditCard, "nameOnCreditCard");
		return nameOnCreditCard;
	}
	
	public IOSElement getExpiryMonthField() {
		objiOSGenericMethods.CheckIOSElementFound(expiryMonthField, "expiryMonthField");
		return expiryMonthField;
	}
	
	public IOSElement getExpiryMonth() {
		objiOSGenericMethods.CheckIOSElementFound(expiryMonth, "expiryMonth");
		return expiryMonth;
	}
	
	public IOSElement getExpiryYear() {
		objiOSGenericMethods.CheckIOSElementFound(expiryYear, "expiryYear");
		return expiryYear;
	}
	
	public IOSElement getCvvField() {
		objiOSGenericMethods.CheckIOSElementFound(cvvField, "cvvField");
		return cvvField;
	}
	
	public IOSElement getExpiryYearField() {
		objiOSGenericMethods.CheckIOSElementFound(expiryYearField, "expiryYearField");
		return expiryYearField;
	}

	public IOSElement getPaymentPageTitle() {
		objiOSGenericMethods.CheckIOSElementFound(paymentPageTitle, "paymentPageTitle");
		return paymentPageTitle;
	}
	
	public IOSElement getWallets() {
		objiOSGenericMethods.CheckIOSElementFound(Wallets, "Wallets");
		return Wallets;
	}
	
	public IOSElement getfirstNetBankingOption() {
		objiOSGenericMethods.CheckIOSElementFound(firstNetBankingOption, "firstNetBankingOption");
		return firstNetBankingOption;
	}
	
	public IOSElement getnetbankingPayNowButton() {
		objiOSGenericMethods.CheckIOSElementFound(netbankingPayNowButton, "netbankingPayNowButton");
		return netbankingPayNowButton;
	}

	public IOSElement getCOD() {
		objiOSGenericMethods.CheckIOSElementFound(COD, "COD");
		return COD;
	}

	public IOSElement getPhonepe() {
		objiOSGenericMethods.CheckIOSElementFound(phonepe, "phonepe");
		return phonepe;
	}

	public IOSElement getCCandDC() {
		objiOSGenericMethods.CheckIOSElementFound(CCandDC, "creditCard/debitCard");
		return CCandDC;
	}

	public IOSElement getGiftCard() {
		objiOSGenericMethods.CheckIOSElementFound(giftCards, "giftCards");
		return giftCards;
	}

	public IOSElement getGiftCardNumber() {
		objiOSGenericMethods.CheckIOSElementFound(giftCardNumber, "giftCardNumber");
		return giftCardNumber;
	}

	public IOSElement getGiftCardpin() {
		objiOSGenericMethods.CheckIOSElementFound(giftCardpin, "giftCardpin");
		return giftCardpin;
	}

	public IOSElement getGiftCardApplyButton() {
		objiOSGenericMethods.CheckIOSElementFound(giftCardApplyButton, "giftCardApplyButton");
		return giftCardApplyButton;
	}

	public IOSElement getChangePaymentMode() {
		objiOSGenericMethods.CheckIOSElementFound(changePaymentMode, "changePaymentMode");
		return changePaymentMode;
	}

	public IOSElement getNetBanking() {
		objiOSGenericMethods.CheckIOSElementFound(netBanking, "netBanking");
		return netBanking;
	}

	/**
	 * methods
	 */
	public void ClickOnWallets() {
		objiOSGenericMethods.clickOnIOSElement(getWallets(), "Wallets");
	}

	public void ClickOnphonepe() {
		objiOSGenericMethods.clickOnIOSElement(getPhonepe(), "Phonepe");
	}

	public void ClickOnCOD() {
		objiOSGenericMethods.clickOnIOSElement(getCOD(), "COD");
	}

	public void clickOnChangePaymentMode() {
		objiOSGenericMethods.clickOnIOSElement(getChangePaymentMode(), "ChangePaymentMode");
	}

	public void clickOnApplyCouponCode() {
		objiOSGenericMethods.clickOnIOSElement(getApplyCouponCode(), "ApplyCouponCode");
	}

	public void clickOnGiftCardApplyButton() {
		objiOSGenericMethods.clickOnIOSElement(getGiftCardApplyButton(), "GiftCardApplyButton");
	}

	public void clickOnGiftcard() {
		objiOSGenericMethods.clickOnIOSElement(getGiftCard(), "giftCards");
	}

	public void enterGiftCardNumber(String name) {
		objiOSGenericMethods.clickOnIOSElement(getGiftCardNumber(), "GiftCardNumber");
		getGiftCardNumber().sendKeys(name);
	}

	public void enterEnterCouponCode(String name) {
		objiOSGenericMethods.clickOnIOSElement(getEnterCouponCode(), "EnterCouponCode");
		getEnterCouponCode().sendKeys(name);
	}

	public void enterGiftCardpin(String name) {
		objiOSGenericMethods.clickOnIOSElement(getGiftCardpin(), "GiftCardpin");
		getGiftCardpin().sendKeys(name);
	}

	public void clickOnNetBanking() {
		objiOSGenericMethods.clickOnIOSElement(getNetBanking(), "NetBanking");
	}

	public void clickOnCCandDC() {
		objiOSGenericMethods.waitDriver(getCCandDC(), "Credit card");
		objiOSGenericMethods.clickOnIOSElement(getCCandDC(), "creditCard/debitCard");
	}
	
	public void clickFirstNetbankingOption(){
		objiOSGenericMethods.clickOnIOSElement(getfirstNetBankingOption(), "first Netbanking Option");
	}
	
	public void clickNetbankingPayNowButton(){
		objiOSGenericMethods.clickOnIOSElement(getnetbankingPayNowButton(), "first Netbanking Pay Now Button");
	}
	
	public void clickOnEMIcreditCard() {
		objiOSGenericMethods.clickOnIOSElement(getEMIcreditCard(), "EMI Credit card Option.");
	}
	
	public void clickEMIselectBanKDrpDwn() {
		objiOSGenericMethods.clickOnIOSElement(getEMIselectBanKDrpDwn(), "EMI credit bank drop down.");
	}
	
	public void clickEMIbankSelect() {
		objiOSGenericMethods.clickOnIOSElement(getEMIbankSelect(), "EMI Credit bank Option.");
	}
	
	public void clickSelectEMIdurationDrpDwn() {
		objiOSGenericMethods.clickOnIOSElement(getSelectEMIdurationDrpDwn(), "select EMI duration dropdown.");
	}
	
	public void clickSelectEMIduration() {
		objiOSGenericMethods.clickOnIOSElement(getSelectEMIduration(), "select EMI duration.");
	}

	public void VerifyPaymentPageTitle()	{
		objiOSGenericMethods.VerifyStringShouldNotEmpty(getPaymentPageTitle(), "Payment Page Title");
	}
	
	public void enterCreditCardDetails(String CardNumber, String Name, String CVV)	{
		creditCardNumber.sendKeys(CardNumber);
		nameOnCreditCard.sendKeys(Name);
		expiryMonthField.click();
		expiryMonth.click();
		expiryYearField.click();
		expiryYear.clear();
		cvvField.sendKeys(CVV);
	}
	
	public void VerifyDeliveryAddress()	{
		objiOSGenericMethods.waitDriver(getdDeliveryAddress(), "Delevery Address");
		objiOSGenericMethods.VerifyStringShouldNotEmpty(getdDeliveryAddress(), "Payment Page Title");
	}
	
	public void SelectEMIbankDuration()	{
		clickEMIselectBanKDrpDwn();
		clickEMIbankSelect();
		clickSelectEMIdurationDrpDwn();
		clickSelectEMIduration();
	}
}
