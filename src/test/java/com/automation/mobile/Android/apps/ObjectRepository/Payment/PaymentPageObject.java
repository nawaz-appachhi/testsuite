package com.automation.mobile.Android.apps.ObjectRepository.Payment;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.automation.core.mobile.Android.AndroidGenericMethods;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.PressesKeyCode;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidKeyCode;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class PaymentPageObject {
	AndroidGenericMethods objAndroidGenericMethods;
	private AppiumDriver<MobileElement> aDriver;

	public PaymentPageObject(AppiumDriver<MobileElement> aDriver) {
		PageFactory.initElements(new AppiumFieldDecorator(aDriver), this);
		objAndroidGenericMethods = new AndroidGenericMethods(aDriver);
		this.aDriver = aDriver;
	}
	String ORDER_NUMBER;

	@FindBy(xpath = "//*[@index='0' and @content-desc='Change Address']")
	public AndroidElement ChangeAddress;
	@FindBy(xpath = "//android.widget.Button[@index='14' and @content-desc='PAY NOW']")
	public AndroidElement PayNow;
	@FindBy(xpath = "//*[@index='9' and @content-desc='DeliveryFREE']")
	public AndroidElement FreeDelivery;
	/**
	 * Object identified for the payment options displayed in the payment page;
	 * Note: Options include Credit/Debit Card, Net Banking, Cash/Card On Delivery,
	 * PhonePe / BHIM UPI, Gift Card, Wallets,EMI (Credit Card);
	 * 
	 * @author 300021278 -Rakesh
	 */
	@FindBy(xpath = "//div[@class='tabs']/ul/li")
	public List<AndroidElement> paymentOptions;
	
	@FindBy(xpath = "//android.widget.Button[@content-desc='CONFIRM ORDER']")
	public AndroidElement confirmOrderbtn;
	
	@FindBy(xpath="//android.widget.TextView[@text='PAYMENT']")
	public AndroidElement paymentHeader;
	
	@FindBy(xpath="//android.view.View[@content-desc='Cash On Delivery']")
	public AndroidElement CODHeader;
	
	@FindBy(className="card-num-input")
	public AndroidElement cardNumberInput;
	
	@FindBy(xpath="//input[@placeholder='Name On Card']")
	public AndroidElement nameOnCard;
	
	@FindBy(xpath="//select[@name='card_month']")
	public AndroidElement expireMonth;
	
	@FindBy(xpath="//select[@name='card_year']")
	public AndroidElement expiryYear;
	
	@FindBy(xpath="//*[@class='card-details']/div[3]/div[5]/input")
	public AndroidElement entercvv;
	
	@FindBy(xpath="//button[contains(text(),'PAY NOW')]")
	public AndroidElement payNowbtn;
	
	/**
	 * Object identified to check Eligibility
	 * 
	 * @author Amba
	 */
	
	@FindBy(xpath="//android.view.View[contains(text(),'Check eligibility']")
	public AndroidElement CheckForEligibility;
	
	public AndroidElement getCheckForEligibility() {
		objAndroidGenericMethods.CheckAndroidElementFoundForWebView(CheckForEligibility, "CheckForEligibility");
		return CheckForEligibility;
	}
	
	/**
	 * Addded by Aishurya: order confirm page header
	 */
	@FindBy(xpath="//android.widget.TextView[@resource-id='com.myntra.android:id/tv_title']")
	public AndroidElement orderConfirmedHeader;
	
	/**
	 * Addded by Aishurya: order confirm text msg
	 */
	@FindBy(className="msg-success")
	public AndroidElement orderConfirmed;
	
	/**
	 * Addded by Aishurya: View order link
	 */
	@FindBy(className="track")
	public AndroidElement ViewOrder;
	
	/**
	 * Addded by Aishurya: order status in order details page
	 */
	@FindBy(xpath="//*[@class='details-panel']/span[2]/div[2]")
	public AndroidElement statusQueued;
	
	/**
	 * Addded by Aishurya: order number in order details page
	 */
	@FindBy(className="order-no-value")
	public AndroidElement orderNumber;
	
	/**
	 * Addded by Aishurya: order number in order details page
	 */
	public AndroidElement getOrderNumber() {
		objAndroidGenericMethods.CheckAndroidElementFoundForWebView(orderNumber, "orderNumber");
		return orderNumber;
	}
	/**
	 * Addded by Aishurya: order status in order details page
	 */
	public AndroidElement getStatusQueued() { 
		objAndroidGenericMethods.CheckAndroidElementFoundForWebView(statusQueued, "statusQueued");
		return statusQueued;
	}
	/**
	 * Addded by Aishurya: View order link
	 */
	public AndroidElement getViewOrder() {
		objAndroidGenericMethods.CheckAndroidElementFoundForWebView(ViewOrder, "ViewOrder");
		return ViewOrder;
	}
	/**
	 * Addded by Aishurya: order confirm text msg
	 */
	public AndroidElement getOrderConfirmed() {
		objAndroidGenericMethods.CheckAndroidElementFoundForWebView(orderConfirmed, "orderConfirmed");
		return orderConfirmed;
	}
	/**
	 * Addded by Aishurya: order confirm page header
	 */
	public AndroidElement getOrderConfirmedHeader() {
		objAndroidGenericMethods.CheckAndroidElementFound(orderConfirmedHeader, "orderConfirmedHeader");
		return orderConfirmedHeader;
	}

	public AndroidElement getPayNowbtn() {
		objAndroidGenericMethods.CheckAndroidElementFoundForWebView(payNowbtn, "payNowbtn");
		return payNowbtn;
	}

	public AndroidElement getEntercvv() {
		objAndroidGenericMethods.CheckAndroidElementFoundForWebView(entercvv, "entercvv");
		return entercvv;
	}

	public AndroidElement getExpiryYear() {
		objAndroidGenericMethods.CheckAndroidElementFoundForWebView(expiryYear, "expiryYear");
		return expiryYear;
	}

	public AndroidElement getExpireMonth() {
		objAndroidGenericMethods.CheckAndroidElementFoundForWebView(expireMonth, "expireMonth");
		return expireMonth;
	}

	public AndroidElement getNameOnCard() {
		objAndroidGenericMethods.CheckAndroidElementFoundForWebView(nameOnCard, "nameOnCard");
		return nameOnCard;
	}

	public AndroidElement getCardNumberInput() {
		objAndroidGenericMethods.CheckAndroidElementFoundForWebView(cardNumberInput, "cardNumberInput");
		return cardNumberInput;
	}

	public AndroidElement getCODHeader() {
		return CODHeader;
	}
	
	public AndroidElement getPaymentHeader() {
		objAndroidGenericMethods.CheckAndroidElementFound(paymentHeader, "paymentHeader");
		return paymentHeader;
	}
	
	public void verifyPaymentHeader() {
		objAndroidGenericMethods.VerifyTwoString(getPaymentHeader(), "PAYMENT");
	}
	 
	public void verifyCODHeader() {
		objAndroidGenericMethods.VerifyTwoString(getCODHeader(), "Cash On Delivery");
	}
	
	public AndroidElement getConfirmOrderbtn() {
		objAndroidGenericMethods.CheckAndroidElementFound(confirmOrderbtn, "confirmOrderbtn found");
		return confirmOrderbtn;
	}
	
	public void ClickOnConfirmOrder() {
		objAndroidGenericMethods.clickOnAndroidElement(getConfirmOrderbtn(), "clicked on getConfirmOrderbtn" );
	}

	/**
	 * getter for paymentOptions
	 * 
	 * @author 300021278 -Rakesh
	 * @return
	 */
	public List<AndroidElement> getPaymentOptions() {
		objAndroidGenericMethods.CheckAndroidElementsListFoundforWebView(paymentOptions, "paymentOptions");
		return paymentOptions;
	}
	/**
	 * Method to click on Check Eligibility 
	 * @author Amba
	 *
	 * @param check Eligibility
	 */
	public void clickCheckForEligibility() {
		objAndroidGenericMethods.clickOnAndroidElementforwebVIew(getCheckForEligibility(), "CheckForEligibility");
	}
	/**
	 * Method to select payment option ; Note: Options include Credit/Debit Card,
	 * Net Banking, Cash/Card On Delivery, PhonePe / BHIM UPI, Gift Card, Wallets,EMI (Credit Card), Cash On Delivery;
	 * 
	 * @author 300021278 -Rakesh
	 *
	 * @param paymentOption
	 */
	public void selectPaymentOption(String paymentOption) {
		//objAndroidGenericMethods.swithchInToWebview();
		List<AndroidElement> options= getPaymentOptions();
		System.out.println("payment options size is" + options.size() );
		for (AndroidElement option: options) {
			String optionValue= option.getText();
			System.out.println("payment options available are" + optionValue );
			if (optionValue.endsWith(paymentOption)) {
			//	objAndroidGenericMethods.clickOnAndroidElementforwebVIew(option, paymentOption);
				objAndroidGenericMethods.scrollDown(option, 100);
				break; 
			}else {
				System.out.println("wrong input");
			}
		}
		//objAndroidGenericMethods.switchInToNativeView();
	}

	public AndroidElement getChangeAddress() {
		return ChangeAddress;
	}

	public AndroidElement getFreeDelivery() {
		return FreeDelivery;
	}

	public void clickOnChangeAddress() {
		getChangeAddress().click();
	}

	public void clickOnFreeDelivery() {
		getFreeDelivery().click();
	}
	/**
	 * this method use for entering the card number on credit/debit card payment page
	 * @author 300021282 VINAY
	 */
	public void enterCardNumber(String CardNumber) {
		objAndroidGenericMethods.enterTexAndroidElement(getCardNumberInput(), CardNumber, "CardNumber");
	}
	public void enterNameOnCard(String NameOnCard) {
		objAndroidGenericMethods.enterTexAndroidElement(getNameOnCard(), NameOnCard, "NameOnCard");
	}
	public void clickExpiryMonts() throws InterruptedException {
		Select oSelect = new Select(expireMonth);
		objAndroidGenericMethods.clickOnAndroidElementforwebVIew(expireMonth, "expireMonth");
		oSelect.selectByValue("08");
		((PressesKeyCode) aDriver).pressKeyCode(AndroidKeyCode.BACK);  		
	}

	public void clickExpiryYears() throws InterruptedException {
		Select oselect = new Select(expiryYear);
		objAndroidGenericMethods.clickOnAndroidElementforwebVIew(expiryYear, "expiryYear");
		oselect.selectByValue("26");
		((PressesKeyCode) aDriver).pressKeyCode(AndroidKeyCode.BACK); 	
	}
	
	public void enterCVVNumber(String cvvno) {
		objAndroidGenericMethods.enterTexAndroidElement(getEntercvv(), cvvno, "cvvno");
	}
	public void clickPayNowBtn() {
		objAndroidGenericMethods.clickOnAndroidElementforwebVIew(getPayNowbtn(), "getPayNow");
	}
	
	/**
	 * Assertion
	 * Modified reason:copied from the phonepe object 
	 */
	
	@FindBy(xpath="//*[@text='Pay by PhonePe' or @resource-id='tab_phonepe' or @content-desc='Pay by PhonePe']")
	public AndroidElement phonepeHeader;
	
	public AndroidElement getPhonepeHeader() {
		return phonepeHeader;
	}
	public void verifyPhonepeHeader() {
		objAndroidGenericMethods.VerifyTwoString(getPhonepeHeader(),"Pay by PhonePe");
	}
	
	@FindBy(xpath = "//*[@content-desc='SELECT Gift Card']")
	public AndroidElement GIFTCARD;
	@FindBy(xpath = "//*[@content-desc='Gift Card Number']")
	public AndroidElement GiftCardNumber;

	
	
	public AndroidElement getGiftCardNumber() {
		objAndroidGenericMethods.CheckAndroidElementFound(GiftCardNumber, "found GiftCardNumber");
		return GiftCardNumber;
	}

	public AndroidElement getGIFTCARD() {
		objAndroidGenericMethods.CheckAndroidElementFound(GIFTCARD, "found GIFTCARD");
		return GIFTCARD;
	}

	public void clickOnGIFTCARD() {
		objAndroidGenericMethods.clickOnAndroidElement(getGIFTCARD(), "clicked on getGIFTCARD");
	}
	
	/******** Assertion ***************/
	@FindBy(xpath="//*[@text='Credit/Debit Card' or @content-desc='Credit/Debit Card']")
	public AndroidElement CreditdebitHeader;
	
	public AndroidElement getCreditdebitHeader() {
		return CreditdebitHeader;
	}
	public void verifyCardHeader() {
		objAndroidGenericMethods.VerifyTwoString(getCreditdebitHeader(), "Credit/Debit Card");
	}

	/**
	 * @author 300019224-Aishurya: To read order number after placing order
	 */
	public void readOrderNumberConfirmationPage() {
		getOrderConfirmedHeader();
		getOrderConfirmed();
		ORDER_NUMBER = getViewOrder().findElement(By.tagName("a")).getAttribute("href").split("=")[1].trim();
		System.out.println(ORDER_NUMBER);
	}
	/**
	 * @author 300019224-Aishurya: To click on view order link in order confirmation page
	 */
	public void clickOnViewOrder() {
		objAndroidGenericMethods.clickOnAndroidElementforwebVIew(getViewOrder(), "ViewOrder Link");
	}
	
	/**
	 * @author 300019224-Aishurya: To verify order number in order details page
	 */
	public void VerifyOrderNumberOrderDetailsPage() {
		objAndroidGenericMethods.VerifyTwoString(getStatusQueued(), "Queued");
		objAndroidGenericMethods.scrollDown(getOrderNumber(), 50);
		objAndroidGenericMethods.VerifyTwoString(getOrderNumber(), ORDER_NUMBER);
	}
}
