package com.automation.mobile.Android.MobileWeb.ObjectRepository.PaymentObjects;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.automation.core.mobile.Android.AndroidGenericMethods;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

/**
 * Modified By:Aishurya:Changed string parameter,which is being used in reporting
 */
public class PaymentPageObjects {

	AndroidGenericMethods objAndroidGenericMethods;
	public PaymentPageObjects(AppiumDriver<MobileElement> aDriver) {
		PageFactory.initElements(new AppiumFieldDecorator(aDriver), this);
		objAndroidGenericMethods = new AndroidGenericMethods(aDriver);
	}

	/*
	 * Giftcards options 300021281
	 */
	@FindBy(xpath = "//li[@id='tab_gift_card']")
	public AndroidElement giftCards;

	@FindBy(xpath = "//input[@id='card-number-txt']")
	public AndroidElement giftCardNumber;

	@FindBy(xpath = ".//input[@id='card-pin-txt']")
	public AndroidElement giftCardpin;

	@FindBy(xpath = ".//button[@id='apply-btn-confirm']")
	public AndroidElement giftCardApplyButton;

	@FindBy(xpath = ".//div[@class='change-pay-mode']")
	public AndroidElement changePaymentMode;
	@FindBy(xpath="//div[text()='Check eligibility']")
	public AndroidElement CheckEligibility;

	/*
	 * Giftcards options 300021281
	 */

	@FindBy(xpath = "//li[@id='tab_net_banking']")
	public AndroidElement netBanking;

	/*
	 * Credit Card
	 */

	@FindBy(id = "tab_credit_card")
	public AndroidElement creditCard;

	/*
	 * Couopn
	 */
	
	@FindBy(xpath = "//input[@class='enter-coupon m-textbox']")
	public AndroidElement EnterCouponCode;
	
	@FindBy(xpath = "//span[@class='apply-coupon text-link link-tappable' and text()='Apply Coupon']")
	public AndroidElement ApplyCoupan;
	//@FindBy(xpath = ".//*[text()='Apply']")
	//btn-apply
	@FindBy(xpath="//button[@name='//btn-apply']")
	public AndroidElement ApplyCouponCode;

	public AndroidElement getEnterCouponCode() {
		return EnterCouponCode;
	}
	public AndroidElement getApplyCoupan() {
		return ApplyCoupan;
	}
	public AndroidElement getApplyCouponCode() {
		return ApplyCouponCode;
	}
	public AndroidElement getCheckEligibility() {
		return CheckEligibility;
	}
	
	/*
	 * PhonePe
	 */
	@FindBy(xpath = "//li[@id='tab_phonepe']")
	public AndroidElement phonepe;

	/*
	 * COD
	 */
	@FindBy(id="tab_cod")
	public AndroidElement COD;

	/*
	 * Wallets
	 */
	@FindBy(xpath = "//li[@id='tab_wallets']")
	public AndroidElement Wallets;

	public AndroidElement getWallets() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(Wallets, "Wallets");
		return Wallets;
	}

	public AndroidElement getCOD() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(COD, "COD");
		return COD;
	}

	public AndroidElement getPhonepe() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(phonepe, "phonepe");
		return phonepe;
	}

	public AndroidElement getcreditCard() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(creditCard, "creditCard");
		return creditCard;
	}

	public AndroidElement getGiftCard() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(giftCards, "giftCards");
		return giftCards;
	}

	public AndroidElement getGiftCardNumber() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(giftCardNumber, "giftCardNumber");
		return giftCardNumber;
	}

	public AndroidElement getGiftCardpin() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(giftCardpin, "giftCardpin");
		return giftCardpin;
	}

	public AndroidElement getGiftCardApplyButton() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(giftCardApplyButton, "giftCardApplyButton");
		return giftCardApplyButton;
	}

	public AndroidElement getChangePaymentMode() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(changePaymentMode, "changePaymentMode");
		return changePaymentMode;
	}

	public AndroidElement getNetBanking() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(netBanking, "netBanking");
		return netBanking;
	}

	/**
	 * methods
	 */
	public void ClickOnWallets() {
		objAndroidGenericMethods.clickOnAndroidElement(getWallets(), "Wallets");
	}
	public void ClickOnCheckEligibility() {
		objAndroidGenericMethods.clickOnAndroidElement(getCheckEligibility(), "Wallets");
	}
	
	public void ClickOnphonepe() {
		objAndroidGenericMethods.clickOnAndroidElement(getPhonepe(), "Phonepe");
	}

	public void ClickOnCOD() {
		objAndroidGenericMethods.clickOnAndroidElement(getCOD(), "COD");
	}

	public void clickOnChangePaymentMode()  {
		objAndroidGenericMethods.clickOnAndroidElement(getChangePaymentMode(), "ChangePaymentMode");
	}

	public void clickOnApplyCouponCode() {
		objAndroidGenericMethods.clickOnAndroidElement(getApplyCouponCode(), "ApplyCouponCode");
	}

	public void clickOnGiftCardApplyButton()  {
		objAndroidGenericMethods.clickOnAndroidElement(getGiftCardApplyButton(), "GiftCardApplyButton");
	}

	public void clickOnGiftcard()  {
		objAndroidGenericMethods.clickOnAndroidElement(getGiftCard(), "giftCards");
	}

	/*
	 * @param giftcardnumber
	 * Modified by : Anu
	 * Description : Removed Hardcoded Giftcard number
	 */
	public void enterGiftCardNumber(String giftcardnumber) {
		objAndroidGenericMethods.clickOnAndroidElement(getGiftCardNumber(), "GiftCardNumber");
		getGiftCardNumber().sendKeys(giftcardnumber);
	}

	public void enterEnterCouponCode() {
		objAndroidGenericMethods.clickOnAndroidElement(getEnterCouponCode(), "EnterCouponCode");
		getEnterCouponCode().sendKeys("MYNTRANEW1000");
	}
	/*
	 * @param giftcardpin
	 * Modified by : Anu
	 * Description : Removed Hardcoded Giftcardpin
	 */
	public void enterGiftCardpin(String giftcardpin) {
		objAndroidGenericMethods.clickOnAndroidElement(getGiftCardpin(), "GiftCardpin");
		getGiftCardpin().sendKeys(giftcardpin);
	}

	public void clickOnNetBanking() {
		objAndroidGenericMethods.clickOnAndroidElement(getNetBanking(), "NetBanking");
	}

	public void clickOnCreditCard() {
		objAndroidGenericMethods.clickOnAndroidElement(getcreditCard(), "creditCard");
	}
	
	
	@FindBy(className="summary-bd")
	public AndroidElement deliveryAddress;
	
	public AndroidElement getdDeliveryAddress() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(deliveryAddress, "deliveryAddress");
		return deliveryAddress;
	}
	public void VerifyDeliveryAddress()	{
		objAndroidGenericMethods.VerifyStringShouldNotEmpty(getdDeliveryAddress(), "Payment Page Title");
	}
	
	
	@FindBy(xpath="//div[@class='bank-list']/div[1]")
	public AndroidElement firstNetBankingOption;
	
	public AndroidElement getfirstNetBankingOption() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(firstNetBankingOption, "firstNetBankingOption");
		return firstNetBankingOption;
	}
	public void clickFirstNetbankingOption(){
		objAndroidGenericMethods.clickOnAndroidElement(getfirstNetBankingOption(), "first Netbanking Option");
	}
	
	
	@FindBy(xpath="//button[@class='btn primary-btn btn-continue green-button clickable undefined mobile']")
	public AndroidElement netbankingPayNowButton;
	
	public AndroidElement getnetbankingPayNowButton() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(netbankingPayNowButton, "netbankingPayNowButton");
		return netbankingPayNowButton;
	}
	
	public void clickNetbankingPayNowButton(){
		objAndroidGenericMethods.clickOnAndroidElement(getnetbankingPayNowButton(), "first Netbanking Pay Now Button");
	}
	public void clickOnEMIcreditCard() {
		objAndroidGenericMethods.clickOnAndroidElement(getEMIcreditCard(), "EMI Credit card Option.");
	}
	public AndroidElement getEMIcreditCard() {
		objAndroidGenericMethods.clickOnAndroidElement(emiCreditCard, "emiCreditCard");
		return emiCreditCard;
	}
	@FindBy(xpath = "//li[@id='tab_emi']")
	public AndroidElement emiCreditCard;
	//below changes are changed by sangam
	public void SelectEMIbankDuration()	{
		clickEMIselectBanKDrpDwn();
		clickEMIbankSelect();
		clickSelectEMIdurationDrpDwn();
		clickSelectEMIduration();
	}
	public void clickEMIselectBanKDrpDwn() {
		objAndroidGenericMethods.clickOnAndroidElement(getEMIselectBanKDrpDwn(), "EMI credit bank drop down.");
	}
	public void clickEMIbankSelect() {
		objAndroidGenericMethods.clickOnAndroidElement(getEMIbankSelect(), "EMI Credit bank Option.");
	}
	public void clickSelectEMIdurationDrpDwn() {
		objAndroidGenericMethods.clickOnAndroidElement(getSelectEMIdurationDrpDwn(), "select EMI duration dropdown.");
	}
	public void clickSelectEMIduration() {
		objAndroidGenericMethods.clickOnAndroidElement(getSelectEMIduration(), "select EMI duration.");
	}
	public AndroidElement getSelectEMIduration() {
		objAndroidGenericMethods.clickOnAndroidElement(selectEMIduration, "selectEMIduration");
		return selectEMIduration;
	}
	public AndroidElement getSelectEMIdurationDrpDwn() {
		objAndroidGenericMethods.clickOnAndroidElement(selectEMIdurationDrpDwn, "selectEMIdurationDrpDwn");
		return selectEMIdurationDrpDwn;
	}

	public AndroidElement getEMIbankSelect() {
		objAndroidGenericMethods.clickOnAndroidElement(emiBankSelect, "emiBankSelect");
		return emiBankSelect;
	}
	public AndroidElement getEMIselectBanKDrpDwn() {
		objAndroidGenericMethods.clickOnAndroidElement(emiSelectBanKDrpDwn, "emiSelectBanKDrpDwn");
		return emiSelectBanKDrpDwn;
	}
	@FindBy(xpath = "//div[@class='emi']")
	public AndroidElement emiSelectBanKDrpDwn;
	@FindBy(xpath = "//option[@value='20']")
	public AndroidElement emiBankSelect;
	@FindBy(xpath = "//select[@id='EMIDurationList_20']")
	public AndroidElement selectEMIdurationDrpDwn;
	@FindBy(xpath = "//option[@value='EMI03']")
	public AndroidElement selectEMIduration;
	
	public void enterCreditCardDetails(String CardNumber, String Name, String CVV)	{
		creditCardNumber.sendKeys(CardNumber);
		nameOnCreditCard.sendKeys(Name);
		expiryMonthField.click();
		expiryMonth.click();
		expiryYearField.click();
		expiryYear.clear();
		cvvField.sendKeys(CVV);
	}
	
	@FindBy(className = "card-num-input")
	public AndroidElement creditCardNumber;
	@FindBy(xpath = "//input[@placeholder='Name On Card']")
	public AndroidElement nameOnCreditCard;
	
	@FindBy(xpath = "//select[@name='card_month']")
	public AndroidElement expiryMonthField;
	
	@FindBy (xpath = "//option[@value='01']")
	public AndroidElement expiryMonth;
	
	@FindBy (xpath = "//select[@name='card_year']")
	public AndroidElement expiryYearField;
	
	@FindBy(xpath = "//option[@value='18']")
	public AndroidElement expiryYear;
	
	@FindBy(xpath = "//input[@placeholder='CVV']")
	public AndroidElement cvvField;
	
}
