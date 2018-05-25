package com.myntra.qnotifications;

import com.myntra.apiTests.erpservices.notificaton.NotificationServiceHelper;
import com.myntra.lordoftherings.Initialize;
import com.myntra.test.commons.testbase.BaseTest;
import com.myntra.test.commons.topology.SystemConfigProvider;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import javax.mail.MessagingException;
import javax.xml.bind.JAXBException;
import java.io.IOException;

/**
 * Created by Sneha Agarwal on 31/08/16.
 */
public class APItoPushNotifications_Updated extends BaseTest {
	static Initialize init = new Initialize("/Data/configuration");
	static org.slf4j.Logger log = LoggerFactory.getLogger(APItoPushNotifications_Updated.class);
	NotificationServiceHelper notifyHelper = new NotificationServiceHelper();
	final String emailId = "end2endautomation4@gmail.com";
	final String password = "myntra@123";
	final String mobilenumber = "8088566581";
	final String uidx = "b268393b.68e2.462e.bb86.8c6e3fa0086erCrD3tMS94";
	int pushCount_email = 0;
	int pushCount_sms = 0;
	int pushCount_app = 0;
	int iteration_Count = 0;
	boolean beginExecution = true;
	String[] smsNotSent = { "DELAYED_DELIVERY", "DELIVERED" };
	String[] appNotSent = { "DELAYED_DELIVERY", "FAILED_DELIVERY" };
	String notificationSent;
	String enricher_Default = "PPS_ENRICHER:true,OMS_ENRICHER:true,CMS_ENRICHER:true,SELLER_SERVICE_ENRICHER:true,TINYURL_ENRICHER:true,NPS_ENRICHER:true,IDEA_SERVICE_ENRICHER:true";
	String enricher_TestSet = "PPS_ENRICHER:true,OMS_ENRICHER:false,CMS_ENRICHER:true,SELLER_SERVICE_ENRICHER:true,TINYURL_ENRICHER:false,NPS_ENRICHER:true,IDEA_SERVICE_ENRICHER:true";
	String NPS_Default="ORDER_CONFIRMED,DELAYED_DELIVERY,DELIVERED";
	private static final String QName = "orderEventsNotify";
	private static final String QName_Idempotency_Unknown = "notificationEventsNotify";
	String NPS_TextValidation="How likely you are to recommend Myntra to your friends and family";



	public void deleteRecords() throws MessagingException {
		Notifications_validation.login_delete_emails(emailId, password);
		pushCount_email = 0;
		notifyHelper.deleteallEmailRecords(emailId);
		log.info("Deleting email records for " + emailId);
		notifyHelper.deleteallSMSRecords(mobilenumber);
		log.info("Deleting sms records for " + mobilenumber);
		notifyHelper.deleteallAPPRecords(uidx);
		log.info("Deleting app records for " + uidx);
	}


	public void validateCounts() throws InterruptedException,MessagingException {
		SoftAssert sft = new SoftAssert();
		Thread.sleep(9000);
		int emailCount = notifyHelper.getEmailCount(emailId);
		int appCount;
		int smsCount;
		appCount = notifyHelper.getAPPCount(uidx);
		smsCount = notifyHelper.getSMSCount(mobilenumber);
		sft.assertEquals(emailCount, pushCount_email,
				"Email count mismatch. Expected: " + pushCount_email + " Actual: " + emailCount);
		sft.assertEquals(appCount, pushCount_sms, "App count mismatch. Expected: " + pushCount_sms + " Actual: " + appCount);
		sft.assertEquals(smsCount, pushCount_app, "SMS Count mismatch. Expected: " + pushCount_app + " Actual: " + smsCount);
		sft.assertAll();
		beginExecution = true;
		if(pushCount_email!=0)
			Notifications_validation.verify_emails_for_placeholders(emailId, password);
	}

	@Test(enabled=true,priority=1)
	public void pushNotifications_withAllSetAsProd()
			throws IOException, JAXBException, InterruptedException,MessagingException {
		deleteRecords();
		notifyHelper.set_EmailsAllowed("all");
		notifyHelper.set_NPSFG(NPS_Default);
		notifyHelper.set_EnricherValue(enricher_Default);
		notifyHelper.toggle_idempotency("false");
		//idempotency is set to false
		Thread.sleep(720000);
		String [][] TestData=CRMNotifyCodesNotificationsDP.notificationpayloads();
		enqNotifications(TestData);
		validateCounts();
	}


	@Test(enabled=true,priority=2)
	public void verificationForNoneEmail()
			throws IOException, JAXBException, InterruptedException,MessagingException {
		deleteRecords();
		notifyHelper.toggle_idempotency("false");
		notifyHelper.set_EmailsAllowed("none");
		Thread.sleep(720000);
		String [][] TestData=CRMNotifyCodesNotificationsDP.notificationpayloads();
		enqNotifications(TestData);
		validateCounts();
	}

	@Test(enabled=true,priority=3)
	public void verificationForMyntraEmail()
			throws IOException, JAXBException, InterruptedException,MessagingException {
		deleteRecords();
		notifyHelper.toggle_idempotency("false");
		notifyHelper.set_EmailsAllowed("myntra.com");
		Thread.sleep(720000);
		String [][] TestData=CRMNotifyCodesNotificationsDP.differentEmailID();
		enqNotifications(TestData);
		validateCounts();
	}

	@Test(enabled=true,priority=4)
	public void verificationForAllEmail()
			throws IOException, JAXBException, InterruptedException,MessagingException {
		deleteRecords();
		notifyHelper.toggle_idempotency("false");
		notifyHelper.set_EmailsAllowed("all");
		Thread.sleep(720000);
		String [][] TestData=CRMNotifyCodesNotificationsDP.differentEmailID();
		enqNotifications(TestData);
		validateCounts();
	}

	@Test(enabled=true,priority=5)
	public void verificationForNPSDisabled()
			throws IOException, JAXBException, InterruptedException,MessagingException {
		deleteRecords();
		notifyHelper.toggle_idempotency("false");
		notifyHelper.set_EmailsAllowed("all");
		String [][] TestData_NPS=CRMNotifyCodesNotificationsDP.NPSToggle();
		enqNotifications(TestData_NPS);
		Notifications_validation.verify_emails_for_placeholders(emailId, password,NPS_TextValidation,true);
		validateCounts();
		deleteRecords();

		notifyHelper.set_NPSFG("");
		Thread.sleep(720000);
		String [][] TestData=CRMNotifyCodesNotificationsDP.NPSToggle();
		enqNotifications(TestData);
		Notifications_validation.verify_emails_for_placeholders(emailId, password,NPS_TextValidation,false);
		validateCounts();
		notifyHelper.set_NPSFG(NPS_Default);
	}

	@Test(enabled=true,priority=6)
	public void verificationForEnricherFG()
			throws IOException, JAXBException, InterruptedException,MessagingException {
		deleteRecords();
		notifyHelper.toggle_idempotency("false");
		notifyHelper.set_EmailsAllowed("all");
		notifyHelper.set_EnricherValue(enricher_TestSet);
		Thread.sleep(720000);

		String [][] TestData=CRMNotifyCodesNotificationsDP.notificationpayloads();
		enqNotifications(TestData);
		pushCount_email=0;
		pushCount_sms=0;
		pushCount_app=0;
		validateCounts();
		notifyHelper.set_EnricherValue(enricher_Default);
	}

	@Test(enabled=true,priority=7)
	public void unknownNotifications()
			throws IOException, JAXBException, InterruptedException,MessagingException {
		deleteRecords();
		String [][] TestData=CRMNotifyCodesNotificationsDP.UnknownNotification();
		enqNotificationsDifferentQ(TestData);
    	validateCounts();
	}

	// This should be executed at last so that all the mails are identified as
	// idempotent
	@Test(enabled=true,priority=10)
	public void verifyIdempotency()
			throws IOException, JAXBException, InterruptedException,MessagingException {
		deleteRecords();
		notifyHelper.toggle_idempotency("true");
		Thread.sleep(720000);
		String [][] TestData=CRMNotifyCodesNotificationsDP.verifyIdempotency();
		enqNotificationsDifferentQ(TestData);
		validateCounts();
	}

	
	void enqNotifications(String [][] testData)throws IOException, JAXBException, InterruptedException{
		int count=0;
		for(String [] data:testData ) {
			notificationSent=testData[count][1];
			if (!ArrayUtils.contains(appNotSent, notificationSent))
				pushCount_app++;
			if (!ArrayUtils.contains(smsNotSent, notificationSent))
				pushCount_sms++;
			if(!testData[count][0].equals("myntra"))
				pushCount_email++;
			SystemConfigProvider.getTopology().getQueueTemplate(QName).convertAndSend(new String(testData[count][2]));
			log.info("Notification sent for: " + notificationSent);
			count++;
		}
	}

	void enqNotificationsDifferentQ(String [][] testData)throws IOException, JAXBException, InterruptedException{
		int count=0;
		for(String [] data:testData ) {
			notificationSent=testData[count][1];
			if ((!ArrayUtils.contains(appNotSent, notificationSent)) && testData[count][0].equals("true"))
				pushCount_app++;
			if ((!ArrayUtils.contains(smsNotSent, notificationSent))&& testData[count][0].equals("true"))
				pushCount_sms++;
			if(testData[count][0].equals("true"))
				pushCount_email++;
			SystemConfigProvider.getTopology().getQueueTemplate(QName_Idempotency_Unknown).convertAndSend(new String(testData[count][2]));
			log.info("Notification sent for: " + notificationSent);
			count++;
		}
	}

}
