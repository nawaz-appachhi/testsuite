package com.automation.mobile.Android.apps.ObjectRepository.Payment;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;


	public class NetBankingPageObject {
		public NetBankingPageObject(AndroidDriver<AndroidElement> aDriver) {
			PageFactory.initElements(new AppiumFieldDecorator(aDriver), this);
		}
		/**
		 * Modified by : Sneha
		 * 
		 * old xpath ://android.view.View[@resource-id='tab_net_banking' ]
		 */

		@FindBy(xpath = "//android.view.View[@text ='SELECTNet Banking' or @resource-id='tab_net_banking' or @index='1']")
		public AndroidElement NetBanking;
		@FindBy(xpath = "//android.view.View[@index='1' and @content-desc='Axis']")
		public AndroidElement AxisBank;
		@FindBy(xpath = "//android.view.View[@index='3' and @content-desc='Citi']")
		public AndroidElement CitiBank;
		@FindBy(xpath = "//android.view.View[@index='5' and @content-desc='HDFC']")
		public AndroidElement HDFC;
		@FindBy(xpath = "//android.view.View[@index='7' and @content-desc='ICICI']")
		public AndroidElement ICICI;
		@FindBy(xpath = "//android.view.View[@index='9' and @content-desc='Kotak']")
		public AndroidElement Kotak;
		@FindBy(xpath = "//android.view.View[@index='11' and @content-desc='SBI']")
		public AndroidElement SBI;
		@FindBy(xpath = "android.widget.Button[@index='1'and @content-desc='PAY NOW']")
		public AndroidElement PayNow;

		public AndroidElement getNetBanking() {
			return NetBanking;
		}

		public AndroidElement getPayNow() {
			return PayNow;
		}

		public AndroidElement getAxisBank() {
			return AxisBank;
		}

		public AndroidElement getCitiBank() {
			return CitiBank;
		}

		public AndroidElement getHDFC() {
			return HDFC;
		}

		public AndroidElement getICICI() {
			return ICICI;
		}

		public AndroidElement getKotak() {
			return Kotak;
		}

		public AndroidElement getSBI() {
			return SBI;
		}

		public void ClickOnNetBanking() {
			getNetBanking().click();
			System.out.println("ClickedOn NetBanking");
		}

		public void ClickOnCitiBank() {
			getCitiBank().click();
			System.out.println("ClickedOn Citi Bank");
		}

		public void ClickOnSBI() {
			getSBI().click();
			System.out.println("ClickedOn SBI");
		}

		public void ClickOnKotak() {
			getKotak().click();
			System.out.println("ClickedOn Kotak");
		}

		public void ClickOnICICI() {
			getICICI().click();
			System.out.println("ClickedOn ICICI");
		}

		public void ClickOnAxisBank() {
			getAxisBank().click();
			System.out.println("ClickedOn AxisBank");
		}

		public void ClickOnPayNow() {
			getPayNow().click();
			System.out.println("ClickedOn AxisBank");
		}
	}

