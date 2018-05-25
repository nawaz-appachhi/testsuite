package com.myntra.apiTests.erpservices.notificaton;


import com.myntra.apiTests.common.Constants.ReleaseStatus;
import com.myntra.apiTests.common.ProcessOrder.Service.ProcessRelease;
import com.myntra.apiTests.common.entries.ReleaseEntry;
import com.myntra.apiTests.common.entries.ReleaseEntryList;
import com.myntra.apiTests.end2end.End2EndHelper;
import com.myntra.apiTests.erpservices.Constants;
import com.myntra.apiTests.erpservices.lms.Constants.PaymentMode;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;

import com.myntra.client.tools.response.ApplicationPropertiesResponse;
import com.myntra.commons.codes.StatusResponse;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.parser.emailParser.Email;
import com.myntra.lordoftherings.parser.emailParser.ParseEmail;
import com.myntra.lordoftherings.parser.emailParser.XMLParser;
//import com.myntra.qnotifications.*;
import com.myntra.test.commons.service.HTTPMethods;
import com.myntra.test.commons.service.HttpExecutorService;
import com.myntra.apiTests.SERVICE_TYPE;
import com.myntra.test.commons.service.Svc;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.testng.Assert;

import javax.mail.MessagingException;
import javax.xml.bind.JAXBException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by abhijit.pati on 12/06/16.
 * Updated by Sneha Agarwal
 */
public class NotificationServiceHelper {

    public static int templateIDEmail=0;
    public static int templateIDSMS=0;
    End2EndHelper e2e = new End2EndHelper();
    OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
    ProcessRelease processRelease = new ProcessRelease();
    XMLParser read = new XMLParser();
    private static Logger log = Logger.getLogger(NotificationServiceHelper.class);
    
    //Notifications_validation notif = new Notifications_validation();
	ParseEmail parse = new ParseEmail();
	Email emails = new Email();
	

    public String getSMSDetailsForRelease(String mobileNo, NotificationTemplate template){
        getTemplateIds(template);
        HashMap<String, Object> notifRecords = (HashMap<String, Object>) DBUtilities.exSelectQueryForSingleRecord("select body from myntra_notification_sms_details where sendto = '"+mobileNo+"' and template_id="+templateIDSMS, "myntra_notification");
        if(notifRecords.size() == 0){
            return null;
        }
        return ""+notifRecords.get("body");
    }

    public String[] getEmailDetailsForReleaseID(String emailID, NotificationTemplate template){
        getTemplateIds(template);
        HashMap<String, Object> notifRecords = (HashMap<String, Object>) DBUtilities.exSelectQueryForSingleRecord("select subject, body from myntra_notification_email_details where sendto = '"+emailID+"' and template_id="+templateIDEmail, "myntra_notification");
        if(notifRecords.size() == 0){
            return null;
        }
        String[] notifEmailRecord = new String[] {""+notifRecords.get("subject"), ""+notifRecords.get("body")};
        return notifEmailRecord;
    }

    private void getTemplateIds(NotificationTemplate template){

        switch (template.toString().toUpperCase()) {
            case "ORDERCORFIRMATION":
                templateIDEmail = 37;
                templateIDSMS = 38;
                break;

            case "QUEUECORFIRMATION":
                    templateIDEmail = 74;
                    templateIDSMS = 75;
                    break;

            case "ORDERDISPATCHED":
                templateIDEmail = 28;
                templateIDSMS = 37;
                break;

            case "ORDERSHIPPEDCOD":
                templateIDEmail = 37;
                templateIDSMS = 37;
                break;

            case "ORDEROUTFORDELIVERY":
                templateIDEmail = 21;
                templateIDSMS = 37;
                break;

            case "ORDERDELIVERED":
                templateIDEmail = 25;
                templateIDSMS = 37;
                break;

            case "RETURNEDCREATED":
                templateIDEmail = 37;
                templateIDSMS = 37;
                break;

            case "EXCHANGECONFIRMED":
                templateIDEmail = 37;
                templateIDSMS = 37;
                break;

            case "EXCHANGESHIPPED":
                templateIDEmail = 37;
                templateIDSMS = 37;
                break;

            case "EXCHNAGEOUTFOREXCHANGE":
                templateIDEmail = 37;
                templateIDSMS = 37;
                break;
            case "RETURNCOMPLETED":
                templateIDEmail = 37;
                templateIDSMS = 37;
                break;
            default:
                break;
        }

    }

    /**
	  * createAndMarkOrderToStatus create the order and marks it to particular status and validate the same and returns orderId
	  * @param skus
	  * @param toStatus
	  * @param checkStatus
	  * @return
	  * @throws Exception
     */
    public String createAndMarkOrderToStatus(String[] skus, ReleaseStatus toStatus, String checkStatus, String addressId) throws Exception{
        End2EndHelper end2EndHelper = new End2EndHelper();
        String orderID = end2EndHelper.createOrder("end2endautomation4@gmail.com", "myntra@123", addressId, skus, "", false, false, false,"", false);
        String orderReleasId = omsServiceHelper.getReleaseId(orderID);
        Assert.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(orderReleasId, "WP", 15));
        Assert.assertTrue(omsServiceHelper.validateOrderOFFHoldStatusInOMS(orderID, 15));
        List<ReleaseEntry> releaseEntries = new ArrayList<>();
        releaseEntries.add(new ReleaseEntry.Builder(omsServiceHelper.getReleaseId(orderID), toStatus).build());
        ReleaseEntryList releaseEntryList= new ReleaseEntryList(releaseEntries);
        processRelease.processReleaseToStatus(releaseEntryList);
        Assert.assertEquals(omsServiceHelper.getOrderReleaseEntry(orderReleasId).getStatus().toString(), checkStatus, "Status in oms is not "+checkStatus);
        return orderID;
    }


    public String createAndMarkOrderToStatus(String login,String Password, String[] skus, ReleaseStatus toStatus, String checkStatus, String addressId, boolean isTod, PaymentMode paymentMode) throws Exception {
	        End2EndHelper end2EndHelper = new End2EndHelper();
	        String orderID = end2EndHelper.createOrder(login, Password, addressId, skus, "", false, false, false, "", isTod, paymentMode);
	        String orderReleasId = omsServiceHelper.getReleaseId(orderID);
	        Assert.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(orderReleasId, "WP", 15));
	        Assert.assertTrue(omsServiceHelper.validateOrderOFFHoldStatusInOMS(orderID, 15));
	        if (!paymentMode.equals(PaymentMode.COD)){
	            end2EndHelper.changePaymentMethodOMS(""+orderID,"on");
	        }
	        log.info("Order ID : " + orderID);
            List<ReleaseEntry> releaseEntries = new ArrayList<>();
            releaseEntries.add(new ReleaseEntry.Builder(omsServiceHelper.getReleaseId(orderID), toStatus).build());
            ReleaseEntryList releaseEntryList= new ReleaseEntryList(releaseEntries);
            processRelease.processReleaseToStatus(releaseEntryList);
	        Assert.assertEquals(omsServiceHelper.getOrderReleaseEntry(orderReleasId).getStatus().toString(), checkStatus, "Status in oms is not " + checkStatus);
	        return orderID;
	    }
	 
	 /**
		 * createAndMarkOrderToStatus
		 * @param skus
		 * @param toStatus
		 * @param checkStatus
		 * @param addressId
		 * @param isTod
		 * @param paymentMode
	     * @return
	     * @throws Exception
	     */
		public String createAndMarkOrderToStatus(String[] skus, ReleaseStatus toStatus, String checkStatus, String addressId, boolean isTod , PaymentMode paymentMode) throws Exception{
			End2EndHelper end2EndHelper = new End2EndHelper();
			String orderID = end2EndHelper.createOrder("end2endautomation4@gmail.com", "myntra@123", addressId, skus, "", false, false, false,"", isTod, paymentMode);
			String orderReleasId = omsServiceHelper.getReleaseId(orderID);
			Assert.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(orderReleasId, "WP", 15));
			Assert.assertTrue(omsServiceHelper.validateOrderOFFHoldStatusInOMS(orderID, 15));
            List<ReleaseEntry> releaseEntries = new ArrayList<>();
            releaseEntries.add(new ReleaseEntry.Builder(omsServiceHelper.getReleaseId(orderID), toStatus).build());
            ReleaseEntryList releaseEntryList= new ReleaseEntryList(releaseEntries);
            processRelease.processReleaseToStatus(releaseEntryList);
			Assert.assertEquals(omsServiceHelper.getOrderReleaseEntry(orderReleasId).getStatus().toString(), checkStatus, "Status in oms is not "+checkStatus);
			return orderID;
		}
		
    public HashMap<String, String> getNotificationsHeaderXML(){
        HashMap<String, String> createOrderHeaders = new HashMap<String, String>();
        createOrderHeaders.put("Authorization", "Basic bG1zYWRtaW5+bG1zYWRtaW46dGVzdA==");
        createOrderHeaders.put("Content-Type", "Application/xml");
        createOrderHeaders.put("Accept", "application/xml");
        return createOrderHeaders;
    }

    // true here means on and false off
    public void toggle_idempotency(String setIdempotencyFlag) throws UnsupportedEncodingException,JAXBException{
        DBUtilities.exUpdateQuery("update core_application_properties set value='"+setIdempotencyFlag +"' where name in ('"+NotificationsFG.notification+"."+NotificationsFG.isIdempotencyCheckEnabled+"');","myntra_tools");
        e2e.refreshToolsApplicationPropertyCache();
        refreshOMSApplicationPropertyCache();
    }
    public void set_EmailsAllowed(String value) throws UnsupportedEncodingException,JAXBException{
        DBUtilities.exUpdateQuery("update core_application_properties set value='"+value +"' where name in ('"+NotificationsFG.notification+"."+NotificationsFG.allowedEmailIdsForNewFlow+"');","myntra_tools");
        e2e.refreshToolsApplicationPropertyCache();
        refreshOMSApplicationPropertyCache();
    }

    public void set_NPSFG(String value) throws UnsupportedEncodingException,JAXBException{
        DBUtilities.exUpdateQuery("update core_application_properties set value='"+value +"' where name in ('"+NotificationsFG.notification+"."+NotificationsFG.npsEnabledEvents+"');","myntra_tools");
        e2e.refreshToolsApplicationPropertyCache();
        refreshOMSApplicationPropertyCache();
    }

    public void set_EnricherValue(String value) throws UnsupportedEncodingException,JAXBException{
        DBUtilities.exUpdateQuery("update core_application_properties set value='"+value +"' where name in ('"+NotificationsFG.notification+"."+NotificationsFG.enricherServiceEnableSet+"');","myntra_tools");
        e2e.refreshToolsApplicationPropertyCache();
        refreshOMSApplicationPropertyCache();
    }



    public void deleteallEmailRecords(String emailID){
        String deleteEmails = "Delete from myntra_notification_email_details where sendto LIKE ('%" + emailID + "%');";
        DBUtilities.exUpdateQuery(deleteEmails, "myntra_notification");
    }
    public void deleteallSMSRecords(String mobilenumber){
        String deletesms = "Delete from notification_sms_details where sendto=" + mobilenumber + ";";
        DBUtilities.exUpdateQuery(deletesms, "myntra_notification");
    }
    public void deleteallAPPRecords(String uidx){
        String deleteapp = "Delete from myntra_notification_app_details where sendto in ('" + uidx + "');";
        DBUtilities.exUpdateQuery(deleteapp, "myntra_notification");
    }


    public int getEmailCount(String emailID){
        String selectEmails="Select * from myntra_notification_email_details where sendto LIKE ('%" + emailID + "%');";
        List<String> emailRecords=DBUtilities.exSelectQuery(selectEmails,"myntra_notification");
        if(emailRecords==null)
            return 0;
        else
            return emailRecords.size();
    }

    public int getSMSCount(String mobilenumber){
        String selectEmails="Select * from myntra_notification_sms_details where sendto=" + mobilenumber + ";";
        List<String> smsRecords=DBUtilities.exSelectQuery(selectEmails,"myntra_notification");
        if(smsRecords==null)
            return 0;
        else
            return smsRecords.size();
    }

    public int getAPPCount(String uidx){
        String selectEmails="Select * from myntra_notification_app_details where sendto in ('" + uidx + "');";
        List<String> appRecords=DBUtilities.exSelectQuery(selectEmails,"myntra_notification");
        if(appRecords==null)
            return 0;
        else
            return appRecords.size();
    }


    public void refreshOMSApplicationPropertyCache() throws UnsupportedEncodingException, JAXBException {
        Svc service = HttpExecutorService.executeHttpService(Constants.OMS_PATH.REFRESHAPPLICATIONPROPERTIES, null, SERVICE_TYPE.OMS_SVC.toString(), HTTPMethods.GET, null, OMSServiceHelper.getOMSHeader());
        ApplicationPropertiesResponse cacheRefresh = (ApplicationPropertiesResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new ApplicationPropertiesResponse());
        Assert.assertEquals(cacheRefresh.getStatus().getStatusType(), StatusResponse.Type.SUCCESS);
    }

    public String getSMS(String mobilenumber,int template_id){
        String selectEmails="Select body from notification_sms_details where sendto=" + mobilenumber + " and template_id="+template_id+" order by id DESC;";
        List smsRecords=DBUtilities.exSelectQuery(selectEmails,"myntra_notification");
        if(smsRecords==null)
            return null;
        else
            return smsRecords.get(0).toString();
    }
    
    
    /**
	 * Get Parsed data by giving relative xpath
	 * @param html,xpath
	 * @return {@link String}
	 * @throws IOException
	 */
    public String getParsedData(String html,String xpath) throws IOException{
    	
    	String parsedData = Jsoup.parse(read.extractTextContentsByXpath(html,xpath)).text().toLowerCase().trim();
    	
		return parsedData;
    	
    }
    
    /**
	 * Login to inbox and return email body
	 * @return {@link Boolean}
	 * @throws IOException
     * @throws MessagingException 
	 */
    public boolean checkEmail(String email,String password,String subject,String xpath,String orderDisplayId, int delaytoCheck) throws IOException, MessagingException{
    	boolean validateStatus = false;
    	
    	emails.login(email, password);
		
    	
		try {
			String html = emails.fetchContent(subject);
            for (int i = 0; i < delaytoCheck; i++) {
                if (getParsedData(html,xpath).contains(orderDisplayId)) {
                    validateStatus = true;
                    break;
                } else {
                    Thread.sleep(5000);
                    validateStatus = false;
                }
                log.info("waiting for email" + i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("End Status After wait");
		return validateStatus;
    	
    }
    /**
	 * WaitCheckEmail
     * @param subject
     * @param email
     * @param delaytoCheck
     * @return
     */
	public boolean WaitCheckEmail(String subject,Email email, int delaytoCheck) {
		log.info("Wait for Email");
		boolean validateStatus = false;
		try {
			for (int i = 0; i < delaytoCheck; i++) {
				
				if (email.fetchContent(subject) != ""){
					validateStatus = true;
					break;
				}else {
					Thread.sleep(7000);
					validateStatus = false;
				}

				log.info("waiting for Email=" + i);
			}
		} catch (Exception e) {
			e.printStackTrace();
			validateStatus = false;
		}
		return validateStatus;
	}
	
    

}
