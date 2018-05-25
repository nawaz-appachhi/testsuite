package com.myntra.qnotifications;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.xml.bind.JAXBException;

import org.apache.commons.lang.ArrayUtils;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.myntra.apiTests.erpservices.Constants;
import com.myntra.apiTests.erpservices.notificaton.NotificationServiceHelper;
import com.myntra.lordoftherings.Initialize;
import com.myntra.test.commons.service.HTTPMethods;
import com.myntra.test.commons.service.HttpExecutorService;
import com.myntra.apiTests.SERVICE_TYPE;
import com.myntra.test.commons.service.Svc;
import com.myntra.test.commons.testbase.BaseTest;

/**
 * Created by Sneha Agarwal on 31/08/16.
 */
public class APItoPushNotifications extends BaseTest {
	static Initialize init = new Initialize("/Data/configuration");
	static org.slf4j.Logger log = LoggerFactory.getLogger(APItoPushNotifications.class);
	NotificationServiceHelper notifyHelper = new NotificationServiceHelper();
	final String emailId = "end2endautomation4@gmail.com";
	final String password = "myntra@123";
	final String mobilenumber = "8088566581";
	final String uidx = "b268393b.68e2.462e.bb86.8c6e3fa0086erCrD3tMS94";
	int pushCount = 0;
	int iteration_Count = 0;
	boolean beginExecution = true;
	String[] smsNotSent = { "DELAYED_DELIVERY", "DELIVERED" };
	String[] appNotSent = { "DELAYED_DELIVERY", "FAILED_DELIVERY" };
	String notificationSent;

	@BeforeMethod
	public void deleteRecords() throws MessagingException {
		//Notifications_validation.login_delete_emails(emailId, password);
		pushCount = 0;
		notifyHelper.deleteallEmailRecords(emailId);
		log.info("Deleting email records for " + emailId);
		notifyHelper.deleteallSMSRecords(mobilenumber);
		log.info("Deleting sms records for " + mobilenumber);
		notifyHelper.deleteallAPPRecords(uidx);
		log.info("Deleting app records for " + uidx);
	}

	@AfterMethod
	public void validateCounts() throws InterruptedException,MessagingException {
		SoftAssert sft = new SoftAssert();
		Thread.sleep(5000);
		int emailCount = notifyHelper.getEmailCount(emailId);
		int appCount;
		int smsCount;
		if (ArrayUtils.contains(appNotSent, notificationSent))
			appCount = 0;
		else
			appCount = notifyHelper.getAPPCount(uidx);
		if (ArrayUtils.contains(smsNotSent, notificationSent))
			smsCount = 0;
		else
			smsCount = notifyHelper.getSMSCount(mobilenumber);
		sft.assertEquals(emailCount, pushCount,
				"Email count mismatch. Expected: " + pushCount + " Actual: " + emailCount);
		sft.assertEquals(appCount, pushCount, "App count mismatch. Expected: " + pushCount + " Actual: " + appCount);
		sft.assertEquals(smsCount, pushCount, "SMS Count mismatch. Expected: " + pushCount + " Actual: " + smsCount);
		sft.assertAll();
		beginExecution = true;
		Notifications_validation.verify_emails_for_placeholders(emailId, password);
	}

	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProviderClass = CRMNotifyCodesNotificationsDP.class, dataProvider = "notificationpayloads", enabled = false, priority = 1)
	public void pushNotifications(boolean enabled, String notification, String payload)
			throws IOException, JAXBException, InterruptedException {
		notifyHelper.toggle_idempotency("false");
		Thread.sleep(720000);
		if (enabled) {
			pushCount++;
			Svc service = HttpExecutorService.executeHttpService(Constants.NOTIFICATIONS_PATH.PUSH_NOTIFICATIONS, null,
					SERVICE_TYPE.NOTIFICATIONS_SVC.toString(), HTTPMethods.POST, payload,
					notifyHelper.getNotificationsHeaderXML());
		}
		notificationSent = notification;
	}

	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProviderClass = CRMNotifyCodesNotificationsDP.class, dataProvider = "differentEmailID", enabled = true, priority = 2)
	public void verificationForNoneEmail(String enabled, String notification, String payload)
			throws IOException, JAXBException, InterruptedException {
		notifyHelper.set_EmailsAllowed("none");
		Thread.sleep(720000);
		pushCount++;
		Svc service = HttpExecutorService.executeHttpService(Constants.NOTIFICATIONS_PATH.PUSH_NOTIFICATIONS, null,
				SERVICE_TYPE.NOTIFICATIONS_SVC.toString(), HTTPMethods.POST, payload,
				notifyHelper.getNotificationsHeaderXML());
		notificationSent = notification;
	}

	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProviderClass = CRMNotifyCodesNotificationsDP.class, dataProvider = "differentEmailID", enabled = true, priority = 3)
	public void verificationForMyntraEmail(String enabled, String notification, String payload)
			throws IOException, JAXBException, InterruptedException {
		notifyHelper.set_EmailsAllowed("myntra");
		Thread.sleep(720000);
		// if (enabled.equals("myntra"))
		pushCount++;
		Svc service = HttpExecutorService.executeHttpService(Constants.NOTIFICATIONS_PATH.PUSH_NOTIFICATIONS, null,
				SERVICE_TYPE.NOTIFICATIONS_SVC.toString(), HTTPMethods.POST, payload,
				notifyHelper.getNotificationsHeaderXML());
		notificationSent = notification;
	}

	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProviderClass = CRMNotifyCodesNotificationsDP.class, dataProvider = "differentEmailID", enabled = true, priority = 4)
	public void verificationForAllEmail(String enabled, String notification, String payload)
			throws IOException, JAXBException, InterruptedException {
		notifyHelper.set_EmailsAllowed("all");
		Thread.sleep(720000);
		pushCount++;
		Svc service = HttpExecutorService.executeHttpService(Constants.NOTIFICATIONS_PATH.PUSH_NOTIFICATIONS, null,
				SERVICE_TYPE.NOTIFICATIONS_SVC.toString(), HTTPMethods.POST, payload,
				notifyHelper.getNotificationsHeaderXML());
		notificationSent = notification;
	}

	// This should be executed at last so that all the mails are identified as
	// idempotent
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProviderClass = CRMNotifyCodesNotificationsDP.class, dataProvider = "notificationpayloads", enabled = true, priority = 5)
	public void verifyidempotency(boolean enabled, String notification, String payload)
			throws IOException, JAXBException, InterruptedException {
		notifyHelper.toggle_idempotency("true");
		Thread.sleep(720000);
		pushCount++;
		if (enabled) {
			Svc service = HttpExecutorService.executeHttpService(Constants.NOTIFICATIONS_PATH.PUSH_NOTIFICATIONS, null,
					SERVICE_TYPE.NOTIFICATIONS_SVC.toString(), HTTPMethods.POST, payload,
					notifyHelper.getNotificationsHeaderXML());
		}
		notificationSent = notification;
	}



}
