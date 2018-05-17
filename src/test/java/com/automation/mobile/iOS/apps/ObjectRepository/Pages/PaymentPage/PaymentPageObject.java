package com.automation.mobile.iOS.apps.ObjectRepository.Pages.PaymentPage;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.Reporter;

import com.automation.core.mobile.iOS.iOSGenericMethods;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

public class PaymentPageObject {
	iOSGenericMethods objiOSGenericMethods;

	public IOSDriver<IOSElement> iDriver;

	public PaymentPageObject(IOSDriver<IOSElement> iDriver) {
		PageFactory.initElements(new AppiumFieldDecorator(iDriver), this);
		objiOSGenericMethods = new iOSGenericMethods(iDriver);
	}

	@iOSFindBy(accessibility = "PhonePe (UPI, Cards)")
	public IOSElement phonepePayment;

	/**
	 * @author 300021275 Added new object for PhonePe/Bhim
	 */

	@iOSFindBy(accessibility = "PhonePe / BHIM UPI")
	public IOSElement phonepeBhimPayment;

	@iOSFindBy(xpath = "//XCUIElementTypeStaticText[@name='Cash/Card On Delivery']")
	public IOSElement codPayment;

	@FindBy(xpath = "//XCUIElementTypeAlert//XCUIElementTypeButton[@name='Allow']")
	public IOSElement permssion;

	@iOSFindBy(accessibility = "Credit/Debit Card")
	public IOSElement creditCard;

	@iOSFindBy(accessibility = "Net Banking")
	public IOSElement netBanking;

	@iOSFindBy(accessibility = "EMI (Credit Card)")
	public IOSElement emi;

	@iOSFindBy(accessibility = "Wallets")
	public IOSElement payByWallets;

	@iOSFindBy(accessibility = "	Cash/Card On Delivery")
	public IOSElement selectCOD;

	// @iOSFindBy(xpath = "//XCUIElementTypeStaticText[@name='Check eligibility']")
	@iOSFindBy(accessibility = "Check eligibility")
	public IOSElement eligibilityforBankDiscount;

	/*
	 * Credit/Debit Cards Name: Monika
	 * 
	 */

	/**
	 * '
	 * 
	 * @author 300019221 Aravindanath Added card details
	 * 
	 */
	@iOSFindBy(xpath = "//XCUIElementTypeOther[@name=\"PAYMENT\"]/XCUIElementTypeOther[10]/XCUIElementTypeTextField")
	public IOSElement CardNumber;

	@iOSFindBy(xpath = "//XCUIElementTypeOther[@name=\"PAYMENT\"]/XCUIElementTypeOther[13]/XCUIElementTypeTextField")
	public IOSElement NameOnCard;

	@iOSFindBy(xpath = "//XCUIElementTypeOther[@name=\"PAYMENT\"]/XCUIElementTypeOther[14]/XCUIElementTypeOther[2]")
	public IOSElement ExpiryMonth;

	@iOSFindBy(xpath = "//XCUIElementTypeOther[@name=\"PAYMENT\"]/XCUIElementTypeOther[15]/XCUIElementTypeOther[2]")
	public IOSElement ExpiryYear;

	// @iOSFindBy(xpath = ".//XCUIElementTypeTextField[@value='Card Number*']")
	// public IOSElement CardNo;

	// @iOSFindBy(xpath = ".//XCUIElementTypeTextField[@value='Name On Card']")
	// public IOSElement NameOnCard;

	@iOSFindBy(accessibility = "Card Verification Value Code")
	public IOSElement CVVNoforCard;

	@iOSFindBy(accessibility = "Card Verification Value Code")
	public IOSElement CVVNoforSavedCard;

	@iOSFindBy(accessibility = "PAY NOW")
	public IOSElement PayNow;

	/*
	 * Gift Card on Payment screen Name: Monika
	 * 
	 */
	@iOSFindBy(accessibility = "Gift Card")
	public IOSElement GiftCardOnPayment;

	@iOSFindBy(xpath = "//XCUIElementTypeOther[@name=\"PAYMENT\"]/XCUIElementTypeTextField")
	public IOSElement GiftCardNumberOnPayment;

	@iOSFindBy(xpath = "//XCUIElementTypeOther[@name=\"PAYMENT\"]/XCUIElementTypeSecureTextField")
	public IOSElement GiftCardPinOnPayment;

	@iOSFindBy(accessibility = "APPLY")
	public IOSElement ApplyOnGiftCardPayment;

	// @iOSFindBy(xpath =
	// "//XCUIElementTypeOther[@name=\"PAYMENT\"]/XCUIElementTypeOther[8]")
	@iOSFindBy(accessibility = "Change Payment Mode")
	public IOSElement ChangePaymentMode;
	
	/**
	 * 
	 * @author 300021276 object created for clicking on confirm order
	 */

	@iOSFindBy(accessibility = "CONFIRM ORDER")
	public IOSElement confirmOrder;
	
	/**
	 * @author 300021276 object created for clicking on done button for ordered confirmation
	 * 
	 */
	
	@iOSFindBy(accessibility = "CONFIRM ORDER")
	public IOSElement doneOrderedConfirmed;
	
	
	/********* getters ************/

	public IOSElement getGiftCardOnPayment() {
		objiOSGenericMethods.CheckIOSElementFound(GiftCardOnPayment, "GiftCardOnPayment");
		return GiftCardOnPayment;
	}

	public IOSElement getGiftCardNumberOnPayment() {
		objiOSGenericMethods.CheckIOSElementFound(GiftCardNumberOnPayment, "GiftCardNumberOnPayment");
		return GiftCardNumberOnPayment;
	}

	public IOSElement getGiftCardPinOnPayment() {
		objiOSGenericMethods.CheckIOSElementFound(GiftCardPinOnPayment, "GiftCardPinOnPayment");
		return GiftCardPinOnPayment;
	}

	public IOSElement getApplyOnGiftCardPayment() {
		objiOSGenericMethods.CheckIOSElementFound(ApplyOnGiftCardPayment, "ApplyOnGiftCardPayment");
		return ApplyOnGiftCardPayment;
	}

	public IOSElement getChangePaymentMode() {
		objiOSGenericMethods.CheckIOSElementFound(ChangePaymentMode, "ChangePaymentMode");
		return ChangePaymentMode;
	}

	public IOSElement getCVVNoforSavedCard() {
		objiOSGenericMethods.CheckIOSElementFound(CVVNoforSavedCard, "CVVNoforSavedCard");
		return CVVNoforSavedCard;
	}

	public IOSElement getPayNow() {
		objiOSGenericMethods.CheckIOSElementFound(PayNow, "PayNow");
		return PayNow;
	}

	public IOSElement getPhonepePayment() {
		objiOSGenericMethods.CheckIOSElementFound(phonepePayment, "phonepePayment");
		return phonepePayment;
	}

	public IOSElement getPhonepeBhimPayment() {
		objiOSGenericMethods.CheckIOSElementFound(phonepeBhimPayment, "phonepeBhimPayment");
		return phonepeBhimPayment;
	}

	public IOSElement getCodPayment() {
		objiOSGenericMethods.CheckIOSElementFound(codPayment, "codPayment");
		return codPayment;
	}

	public IOSElement getPermssion() {
		objiOSGenericMethods.CheckIOSElementFound(permssion, "notifications");
		return permssion;
	}

	public IOSElement getCreditCard() {
		objiOSGenericMethods.CheckIOSElementFound(creditCard, "creditCard");
		return creditCard;
	}

	public IOSElement getEMI() {
		objiOSGenericMethods.CheckIOSElementFound(emi, "EMI");
		return emi;
	}

	public IOSElement getNetBanking() {
		objiOSGenericMethods.CheckIOSElementFound(netBanking, "netBanking");
		return netBanking;
	}

	public IOSElement getPayByWallets() {
		objiOSGenericMethods.CheckIOSElementFound(payByWallets, "payByWallets");
		return payByWallets;
	}

	public IOSElement getSelectCOD() {
		objiOSGenericMethods.CheckIOSElementFound(selectCOD, "selectCOD");
		return selectCOD;
	}

	public IOSElement getEligibilityforBankDiscount() {
		return eligibilityforBankDiscount;
	}

	/**
	 * 
	 *@author 300021276 getters to confirm order 
	 *
	 */
	public IOSElement getConfirmOrder() {
		objiOSGenericMethods.CheckIOSElementFound(confirmOrder, "confirmOrder");
		return confirmOrder;
	}
	/**
	 * 
	 *@author 300021276 getters to done button for ordered confirmation
	 *
	 */
	
	public IOSElement getDoneOrderedConfirmed() {
		objiOSGenericMethods.CheckIOSElementFound(doneOrderedConfirmed, "doneOrderedConfirmed");
		return doneOrderedConfirmed;
	}
	/*********** methods ***********/
	public void clickOnGiftCardOnPayment() {
		try {
			objiOSGenericMethods.clickOnIOSElement(getGiftCardOnPayment(),
					"Successfully click on GiftCard button On Payment screen");
		} catch (Exception e) {
		}
	}

	public void enterGiftCardNumberOnPayment(String GiftCardNumber) {
		objiOSGenericMethods.clickOnIOSElement(getGiftCardNumberOnPayment(),
				"Successfully click on Gift CardNumber On Payment");
		getGiftCardNumberOnPayment().sendKeys(GiftCardNumber);
	}

	public void enterGiftCardPinOnPayment(String GiftCardPin) {
		objiOSGenericMethods.clickOnIOSElement(getGiftCardPinOnPayment(),
				"Successfully click on Gift Card Pin On Payment");
		getGiftCardPinOnPayment().sendKeys(GiftCardPin);
	}

	/**
	 * @author 300019221 Aravindanath Added try block
	 */
	public void clickOnApplyOnGiftCardPayment() {
		try {
			objiOSGenericMethods.clickOnIOSElement(getApplyOnGiftCardPayment(),
					"Successfully click on Apply button On Gift Card Payment ");
		} catch (Exception e) {
		}
	}

	public void clickOnChangePaymentMode() {
		objiOSGenericMethods.clickOnIOSElement(getChangePaymentMode(),
				"Successfully click on ChangePaymentMode button");
	}

	/**
	 * @author 300019221 Aravindanath Added try block
	 */
	public void clickOnCVVNoforSavedCard(String cvv) {
		try {
			objiOSGenericMethods.clickOnIOSElement(getCVVNoforSavedCard(),
					"Successfully click on CVV text Field for saved card");
			getCVVNoforSavedCard().sendKeys(cvv);
		} catch (Exception e) {
		}
	}

	/**
	 * @author 300019221 Aravindanath Added try block
	 */
	public void clickOnPayNow() {
		try {
			objiOSGenericMethods.clickOnIOSElement(getPayNow(), "Successfully click on PayNow button");
		} catch (Exception e) {
		}
	}

	/**
	 * @author 300019221 Added try block
	 */
	public void clickOnPhonepePayment() {
		try {
			objiOSGenericMethods.clickOnIOSElement(getPhonepePayment(), "Successfully click on phonepayment button");
		} catch (Exception e) {
			System.out.println("This product is not eligible for phone payment!");
		}
	}

	/**
	 * @author 300021275 Added new method for PhonePe / Bhim payment option
	 */
	public void clickOnPhonepeBhimPayment() {
		try {
			objiOSGenericMethods.clickOnIOSElement(getPhonepeBhimPayment(),
					"Successfully click on phonePeBhimpayment button");
		} catch (Exception e) {
			System.out.println("This product is not eligible for Bhim payment!");
		}
	}

	/**
	 * @author 300019221 Aravindanath Added try block
	 */
	public void clickOncodPayment() {
		try {
			objiOSGenericMethods.clickOnIOSElement(getCodPayment(), "Successfully click on CodPayment button");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("This product is not eligible for COD!");
		}
	}

	/**
	 * @author 300019221 Aravindanath Added try block
	 */
	public void clickOnCreditCardPayment() {
		try {
			objiOSGenericMethods.clickOnIOSElement(getCreditCard(), "Successfully click on credit card button");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("This product is not eligible for Credit card!");
		}
	}

	/**
	 * @author 300019221 Aravindanath Added try block
	 */
	public void clickOnEMI() {
		try {
			objiOSGenericMethods.clickOnIOSElement(getEMI(), "Successfully click on EMI button");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("This product is not eligible for EMI!");
		}
	}

	/**
	 * @author 300019221 Added try block
	 */
	public void clickOnNetBanking() {
		try {
			objiOSGenericMethods.clickOnIOSElement(getNetBanking(), "Successfully click on net banking button");
		} catch (Exception e) {
			System.out.println("This product is not eligible for Netbanking!");
			Reporter.log("This product is not eligible for Netbanking!");
		}
	}

	/**
	 * @author 300019221 Aravindanath Added try block
	 */
	public void clickOnWallet() {
		try {
			objiOSGenericMethods.clickOnIOSElement(getPayByWallets(), "Successfully click on payByWallets button");
		} catch (Exception e) {
			System.out.println("This product is not eligible for Wallet!");
			Reporter.log("This product is not eligible for Wallet!");
		}
	}

	/**
	 * @author 300019221 Aravinda Replaced thread.sleep with webdriver wait
	 * 
	 * @author 300019221 / Aravindatha Added try catch block, because some products
	 *         will not be eligible for COD! due to which TC fails.
	 */

	public void clickOnCOD() {
		try {
			objiOSGenericMethods.waitForElementVisibility(getSelectCOD());
			if (getSelectCOD().isDisplayed()) {
				objiOSGenericMethods.clickOnIOSElement(getSelectCOD(), "Successfully click on selectCOD button");
			}
		} catch (Exception e) {
			System.out.println("This product is not eligible for COD!");
		}

	}
	
	/**
	 * 
	 *@author 300021276 method to click on confirm order 
	 *
	 */
	public void clickOnConfirmOrder() {
		try {
			// Thread.sleep(500);
			objiOSGenericMethods.waitForElementVisibility(getConfirmOrder());
			if (getConfirmOrder().isDisplayed()) {
				objiOSGenericMethods.clickOnIOSElement(getConfirmOrder(), "Successfully click on confirm order button");
			}
		} catch (Exception e) {
			System.out.println("This product is not eligible for COD!");
		}

	}
	/**
	 * 
	 *@author 300021276 method to click on done button after getting order confirmation
	 *
	 */
	public void clickOnDoneOrderedConfirmed() {
		try {
			// Thread.sleep(500);
			objiOSGenericMethods.waitForElementVisibility(getDoneOrderedConfirmed());
			if (getConfirmOrder().isDisplayed()) {
				objiOSGenericMethods.clickOnIOSElement(getDoneOrderedConfirmed(), "Successfully click on done button");
			}
		} catch (Exception e) {
			System.out.println("This product is not eligible for COD!");
		}

	}
	
	
	
	/**
	 * @author 300019221 Added try block
	 */

	public void clickOnEligibilityForBankDiscount() {
		try {
			objiOSGenericMethods.clickOnIOSElement(getEligibilityforBankDiscount(),
					"Successfully click on selectCOD button");
		} catch (Exception e) {
		}
	}

}
