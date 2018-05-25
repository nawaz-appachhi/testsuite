package com.myntra.apiTests.end2end;

import com.myntra.apiTests.SERVICE_TYPE;
import com.myntra.apiTests.common.Constants.EnumSCM;
import com.myntra.apiTests.common.Constants.ReleaseStatus;
import com.myntra.apiTests.common.Constants.ShipmentSource;
import com.myntra.apiTests.common.ExceptionHandler;
import com.myntra.apiTests.common.ProcessOrder.Service.ProcessRelease;
import com.myntra.apiTests.common.entries.ReleaseEntry;
import com.myntra.apiTests.common.entries.ReleaseEntryList;
import com.myntra.apiTests.erpservices.Constants;
import com.myntra.apiTests.erpservices.atp.ATPServiceHelper;
import com.myntra.apiTests.erpservices.bounty.BountyServiceHelper;
import com.myntra.apiTests.erpservices.ims.IMSServiceHelper;
import com.myntra.apiTests.erpservices.lms.Helper.LMSHelper;
import com.myntra.apiTests.erpservices.lms.Helper.LMS_ReturnHelper;
import com.myntra.apiTests.erpservices.lms.Helper.LmsServiceHelper;
import com.myntra.apiTests.erpservices.notificaton.NotificationServiceHelper;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.erpservices.partners.SellerPaymentServiceHelper;
import com.myntra.apiTests.erpservices.partners.SellerServicesHelper;
import com.myntra.apiTests.erpservices.rms.RMSServiceHelper;
import com.myntra.apiTests.erpservices.wms.WMSServiceHelper;
import com.myntra.apiTests.portalservices.pps.PPSServiceHelper;
import com.myntra.apiTests.portalservices.styleservice.StyleServiceApiHelper;
import com.myntra.commons.codes.StatusResponse;
import com.myntra.commons.codes.StatusResponse.Type;
import com.myntra.commons.exception.ManagerException;
import com.myntra.lordoftherings.SlackMessenger;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.oms.client.entry.OrderEntry;
import com.myntra.paymentplan.domain.response.ExchangeOrderResponse;
import com.myntra.qnotifications.Notifications_validation;
import com.myntra.qnotifications.Xpath_Constants.DELIVERED;
import com.myntra.qnotifications.Xpath_Constants.ORDER_CONFIRM;
import com.myntra.qnotifications.Xpath_Constants.TESTE2ECREATEORDER;
import com.myntra.qnotifications.Xpath_Constants.TESTE2EMARKORDERDELIEVERED;
import com.myntra.returns.common.enums.RefundMode;
import com.myntra.returns.common.enums.ReturnMode;
import com.myntra.returns.response.ReturnResponse;
import com.myntra.sellers.response.SellerWarehouseResponse;
import com.myntra.test.commons.service.HTTPMethods;
import com.myntra.test.commons.service.HttpExecutorService;
import com.myntra.test.commons.service.Svc;
import com.myntra.test.commons.testbase.BaseTest;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by abhijit.pati on 27/05/16.
 */
public class End2EndFlowTest extends BaseTest {

    static org.slf4j.Logger log = LoggerFactory.getLogger(End2EndFlowTest.class);

    NotificationServiceHelper notificationServiceHelper = new NotificationServiceHelper();
    End2EndHelper end2EndHelper = new End2EndHelper();
    BountyServiceHelper bountyServiceHelper = new BountyServiceHelper();
    ATPServiceHelper atpServiceHelper = new ATPServiceHelper();
    IMSServiceHelper imsServiceHelper = new IMSServiceHelper();
    Notifications_validation notif = new Notifications_validation();
    ProcessRelease processRelease = new ProcessRelease();
    LMS_ReturnHelper lms_returnHelper =new  LMS_ReturnHelper();
    OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
	WMSServiceHelper wmsServiceHelper = new WMSServiceHelper();
	LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
	SellerServicesHelper sellerServicesHelper = new SellerServicesHelper();
	
    static enum COMPONENTS { PPS, OMS, WMS, LMS, RMS, SELLER };
    int[] styles = { 373905, 267511, 371389, 1550};

    String loginTestE2ECreateOrder = "end2endauto1@gmail.com";
    String passwordTestE2ECreateOrder = "myntra123";

    String loginTestE2EMarkOrderDelivered = "end2endauto2@gmail.com";
    String passwordTestE2EMarkOrderDelivered = "myntra123";

    String loginTestE2EProcessReturn = "end2end3@myntra.com";
    String passwordTestE2EProcessReturn = "123456";

    String loginTestE2EProcessExchange = "end2end4@myntra.com";
    String passwordTestE2EProcessExchange = "123456";

    String loginTestE2EProcessRTO = "end2end5@myntra.com";
    String passwordTestE2EProcessRTO = "123456";

    String loginTestE2EProcessLost = "end2end6@myntra.com";
    String passwordTestE2EProcessLost = "123456";

    String loginProcessReturnThroughLMSFlow = "end2end7@myntra.com";
    String passwordProcessReturnThroughLMSFlow = "123456";

    String loginTestE2EMarkTODOrderDelivered = "end2end8@myntra.com";
    String passwordTestE2EMarkTODOrderDelivered = "123456";

    String loginTestCreateEGCOrder = "end2end9@myntra.com";
    String passwordTestCreateEGCOrder = "123456";
    String pincode = "560068";
    String lmsGroupNames=" @madan.kn @Sandeep Kaul @rajesh.vemula @gloria.rampur \n";
    String lmsGroup=" <@U0XPD8Z7H> <@U7NCKM5MX> <@U4P6PBACV> <@U3Y09Q2GG> \n";
    String wmsGroupNames="@jadavkirti.k  @arun.g   @rajesh.vemula @sneha.agarwal @prabhakar.s  \n";
    String wmsGroup="<@U4U55KEKC> <@U1P8WDM5H> <@U4P6PBACV> <@U1PUP5PTR> <@U4DVAGMT2> \n";
    String omsGroupNames=" @Manoj.Kansal @rahul.kaura  @rajesh.vemula @chandra.shekhar3 \n";
    String omsGroup="<@U6YBZFACV> <@U0CR3BCR0> <@U4P6PBACV> <@U1X9N2PEZ>  \n";
    String sellerGroupNames=" @Sandal @Nishant.Kashyap @rajesh.vemula \n";
    String sellerGroup="<@U67PE5YQ7> <@U7JNEH38B> <@U4P6PBACV> \n";

    String channelName = System.getenv("ChannelName");
    String envt=System.getenv("environment");
    String channelPostToError = "stage_sanity";
    SlackSession session;
    HashMap<String, String> headers_slack = new HashMap<String, String>() {{
        put("Content-Type","application/json");
    }};

    @BeforeSuite(enabled = false)
    public void testBeforeSuite() throws JAXBException, IOException {
    	StyleServiceApiHelper styleServiceApiHelper = new StyleServiceApiHelper();
        end2EndHelper.updateToolsApplicationProperties("cod.limit.range", "1-200000", "oms");
        end2EndHelper.updateToolsApplicationProperties("bounty.get_default_address", "true", "oms");
        //Deleting Old corrupted Data from ATP and IMS
        String deleteAtpQuery = "delete from inventory where sku_id in ('1243744','895234','1251868')";
        DBUtilities.exUpdateQuery(deleteAtpQuery, "myntra_atp");
        
        String deleteImsQuery = "delete from wh_inventory where sku_id in ('1243744','895234','1251868')";
        DBUtilities.exUpdateQuery(deleteImsQuery, "myntra_atp");
        
        atpServiceHelper.updateInventoryDetails(new String[] { "1243744:28,36:1000:0","895234:46:10000:0","1251868:45:10000:0" },"ON_HAND");
        imsServiceHelper.updateInventory(new String[] { "1243744:28:1000:0", "1243744:36:1000:0"
        		,"895234:46:10000:0","1251868:45:10000:0"},"ON_HAND");
        bountyServiceHelper.refreshBountyApplicationPropertyCache();
        styleServiceApiHelper.styleReindexForStyleIDsPost(styles);
        
    }

    @BeforeMethod(enabled = false)
    public void beforeTest() {
        SlackMessenger.connect();
    }

    @AfterMethod(enabled = false)
    public void afterTest() {
        SlackMessenger.disconnect();
    }

/*    @BeforeMethod
    public void deleteNotificationBeforeStartTest() {
        DBUtilities.exUpdateQuery("Delete from myntra_notification_email_details where sendto='" + loginTestE2ECreateOrder + "';", "myntra_notification");
        DBUtilities.exUpdateQuery("Delete from  myntra_notification_sms_details where sendto='9886373389';", "myntra_notification");
    }*/
    
    
	/**
	 * This function will validate if service is running and giving 200 response
	 * @throws JAXBException
	 * @throws IOException
	 * @throws ManagerException
	 */
	public void validateHealthCheckofServices() throws JAXBException, IOException, ManagerException{
		String errorMessage = null;
		//Validate omsservice
		Svc response = omsServiceHelper.refreshOMSApplicationPropertyCache();
		if(response.getResponseStatus() != 200){
			errorMessage = "`OMS service is down giving "+response.getResponseStatus()+" on "+envt+"`\n"+omsGroup;
		}
		//Validate munshi service
		response = omsServiceHelper.refreshMunshiApplicationPropertyCache();
		if(response.getResponseStatus() != 200){
			if(errorMessage!=null){
				errorMessage += "`Munshi service is down giving "+response.getResponseStatus()+" on "+envt+"`\n" +omsGroup;
			}else{
				errorMessage = "`Munshi service is down giving "+response.getResponseStatus()+" on "+envt+"`\n" +omsGroup;
			}
			
		}
		//Validate wormsService
		response = wmsServiceHelper.refreshWormsApplicationPropertyCache();
		if(response.getResponseStatus() != 200){
			if(errorMessage!=null){
				errorMessage += "`Worms service is down giving "+response.getResponseStatus()+" on "+envt+"`\n"+wmsGroup;
			}else{
				errorMessage = "`Worms service is down giving "+response.getResponseStatus()+" on "+envt+"`\n"+wmsGroup;
			}
		}
		//Validate Wmsservice
		response = wmsServiceHelper.refreshWMSApplicationPropertyCache();
		if(response.getResponseStatus() != 200){
			if(errorMessage!=null){
				errorMessage += "`WMS service is down giving "+response.getResponseStatus()+" on "+envt+"`\n"+wmsGroup;
			}else{
				errorMessage = "`WMS service is down giving "+response.getResponseStatus()+" on "+envt+"`\n"+wmsGroup;
			}
		}
		//Validate LMS service
		response = lmsServiceHelper.refreshLMSApplicationPropertyCache();
		if(response.getResponseStatus() != 200){
			if(errorMessage!=null){
				errorMessage += "`LMS service is down giving "+response.getResponseStatus()+" on "+envt+"`\n"+lmsGroup;
			}else{
				errorMessage = "`LMS service is down giving "+response.getResponseStatus()+" on "+envt+"`\n"+lmsGroup;
			}
		}
		//Validate tms service
		/*response = lmsServiceHelper.refreshTMSApplicationPropertyCache();
		if(response.getResponseStatus() != 200){
			if(errorMessage!=null){
				errorMessage += "`TMS service is down giving "+response.getResponseStatus()+" on "+envt+"`\n"+lmsGroup;
			}else{
				errorMessage = "`TMS service is down giving "+response.getResponseStatus()+" on "+envt+"`\n"+lmsGroup;
			}
		}*/
		//Validate sellerServices service
		SellerWarehouseResponse sellerResponse = sellerServicesHelper.getWarehouseBySeller("21");
		if(!sellerResponse.getStatus().getStatusType().toString().equalsIgnoreCase(Type.SUCCESS.toString())){
			if(errorMessage!=null){
				errorMessage += "`Seller service is giving error "+sellerResponse.getStatus().getStatusType()+" on "+envt+"`\n"+sellerGroup;
			}else{
				errorMessage = "`Seller service is giving error "+sellerResponse.getStatus().getStatusType()+" on "+envt+"`\n"+sellerGroup;
			}
			
		}
		
		if(errorMessage!=null){
		    if(envt.contains("scmqa")) {
                String payload = "{\"channel\": \"#scmqa_dockins-issue\", \"username\": \"testingBot\", \"text\": \""+errorMessage+"\", \"icon_emoji\": \":integration_test:\"}";
                HttpExecutorService.executeHttpService(Constants.SLACK_PATH.SCMQA_DOCKINS_ISSUES, null, SERVICE_TYPE.SLACK.toString(), HTTPMethods.POST, payload,headers_slack);
            }
            else if(envt.contains("mjint")){
                String payload = "{\"channel\": \"#mjint_dockins-issue\", \"username\": \"testingBot\", \"text\": \""+errorMessage+"\", \"icon_emoji\": \":integration_test:\"}";
                HttpExecutorService.executeHttpService(Constants.SLACK_PATH.MJINT_DOCKINS_ISSUES, null, SERVICE_TYPE.SLACK.toString(), HTTPMethods.POST, payload,headers_slack);
            }
		     else
                session.sendMessage(session.findChannelByName(channelPostToError), errorMessage);
			//log.info("Quiting the job");
			//System.exit(0);

		}else{
			log.info("Health check successful proceeding with test execution");
		}
			
	}

    @Test(enabled = true,groups={"sanityjob"})
    public void sanity_createAndMarkOrderDelivered() throws Exception{
        session = SlackSessionFactory.createWebSocketSlackSession("xoxb-244100198310-BG6bIWloaENbL8NzaLi6qQba");
        session.connect();
        String orderId = null;
        try {
    		//Validate healthcheck and stop is there is any error
            //validateHealthCheckofServices();
            
            orderId = end2EndHelper.createOrder(loginTestE2ECreateOrder, passwordTestE2ECreateOrder, pincode, new String[]{"1243744:1"}, "", false, false, false, "", false);
            String orderReleasId = omsServiceHelper.getReleaseId(orderId);
            //Hack for making order unhold till environment issue is fixed
            omsServiceHelper.checkReleaseStatusForOrderWithHack(orderId, EnumSCM.WP);
            ExceptionHandler.handleTrue(omsServiceHelper.validateReleaseStatusInOMS(orderReleasId, "WP", 15));
            ExceptionHandler.handleTrue(omsServiceHelper.validateOrderOFFHoldStatusInOMS(orderId, 15));

           //String orderReleasId="2147613904463";
            List<ReleaseEntry> releaseEntries = new ArrayList<>();
            releaseEntries.add(new ReleaseEntry.Builder(orderReleasId, ReleaseStatus.PK).shipmentSource(ShipmentSource.MYNTRA).build());
            processRelease.processReleaseToStatus(processRelease.getReleaseEntryList(releaseEntries));
            
            String status = omsServiceHelper.getOrderReleaseEntry(orderReleasId).getStatus();            
            if(status.equalsIgnoreCase(EnumSCM.DL)){
                session.sendMessage(session.findChannelByName(channelName), "Order delivered successfully. orderId: "+orderId +" on "+envt+"`\n");
            }
        }catch (Exception e){
         /*   session.sendMessage(session.findChannelByName("stage_sanity"), "Sanity failed for orderId: "+orderId+". Please check the last build of the job: \n" +
                    "http://scmtestautomation1.myntra.com:6060/view/TestTools/job/stageOrderSanity/" );
          */  throw new Exception(e);
        }
    }
    



    @Test(description = "End to End Create Order", priority = 0, enabled = false)
    public void testE2ECreateOrder() throws Exception {
    	OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
        LMSHelper lmsHepler = new LMSHelper();

        SlackMessenger.send("scm_e2e_order_sanity", ">*`End to End Create Order`*");
        try {
            End2EndHelper end2EndHelper = new End2EndHelper();
            notif.login_delete_emails(loginTestE2ECreateOrder, passwordTestE2ECreateOrder);
            String orderID = end2EndHelper.createOrder(loginTestE2ECreateOrder, passwordTestE2ECreateOrder, pincode, new String[]{"1243744:1"}, "", false, false, false, "", false);
            SlackMessenger.send("scm_e2e_order_sanity", "Order created " + orderID);
            String orderReleaseId = omsServiceHelper.getReleaseId(orderID);
            boolean status = omsServiceHelper.validateReleaseStatusInOMS(orderReleaseId, "WP", 15);
            if (!status) {
                SlackMessenger.send("scm_e2e_order_sanity", "`Order status is not WP for Order ID - " + orderID + "`", COMPONENTS.OMS.ordinal());
                Assert.fail("Order Status is Not WP");
            }
            SlackMessenger.send("scm_e2e_order_sanity", "Order status WP");
            Assert.assertTrue(omsServiceHelper.validateOrderOFFHoldStatusInOMS(orderID, 15));
            lmsHepler.insertTestOrder(orderID, null, "DL");
            //String[] body = notificationServiceHelper.getEmailDetailsForReleaseID(loginTestE2ECreateOrder, NotificationTemplate.ORDERCORFIRMATION);
            //Assert.assertNotNull(body, "Notification not Present in DB");
            //String smsBody = notificationServiceHelper.getSMSDetailsForRelease("9886373389", NotificationTemplate.ORDERCORFIRMATION);
            //Assert.assertNotNull(smsBody, "Notification not Present in DB");
            //SlackMessenger.send("scm_e2e_order_sanity", "SMS Notification Sent to Customer for Order ID - "+orderID);
            SlackMessenger.send("scm_e2e_order_sanity", "Checking Email Notification");
            OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
            
            boolean status2 = notificationServiceHelper.checkEmail(loginTestE2ECreateOrder, passwordTestE2ECreateOrder, TESTE2ECREATEORDER.ORDER_CONFIRM, ORDER_CONFIRM.ORDER_ID,orderEntry.getDisplayStoreOrderId().toString(), 5);
            if (!status2) {
                SlackMessenger.send("scm_e2e_order_sanity", "Email not present");
                Assert.fail("Email Notification Missing");
            }
            SlackMessenger.send("scm_e2e_order_sanity", "Email present");
            
        } catch (Exception ex) {
            SlackMessenger.send("scm_e2e_order_sanity", "`Error: " + ex.getMessage() + "`", COMPONENTS.OMS.ordinal());
            throw new Exception(ex);
        }
    }

    @Test(description = "End to End Create And Mark Order Delivered with price validation`", priority =0, enabled = false)
    public void testE2EMarkOrderDelivered() throws Exception {
        OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
        LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
        LMSHelper lmsHelper = new LMSHelper();
        SellerPaymentServiceHelper spsHelper = new SellerPaymentServiceHelper();
        try {
            SlackMessenger.send("scm_e2e_order_sanity", ">*`End to End Create And Mark Order Delivered`*");
            End2EndHelper end2EndHelper = new End2EndHelper();
            notif.login_delete_emails(loginTestE2EMarkOrderDelivered, passwordTestE2EMarkOrderDelivered);
            String orderID = end2EndHelper.createOrder(loginTestE2EMarkOrderDelivered, passwordTestE2EMarkOrderDelivered, "6138447", new String[]{"1243744:1"}, "", false, false, false, "", false);
            SlackMessenger.send("scm_e2e_order_sanity", "Order created " + orderID);
            String orderReleaseId = omsServiceHelper.getReleaseId(orderID);
            boolean status = omsServiceHelper.validateReleaseStatusInOMS(orderReleaseId, "WP", 15);
            if (!status) {
                SlackMessenger.send("scm_e2e_order_sanity", "`Order status is not WP for Order ID - " + orderReleaseId + "`", COMPONENTS.OMS.ordinal());
                Assert.fail("Order Status is Not WP");
            }
            SlackMessenger.send("scm_e2e_order_sanity", "Order status WP");
            Assert.assertTrue(omsServiceHelper.validateOrderOFFHoldStatusInOMS(orderID, 15), "Unable to OFFHold status in OMS");
            List<ReleaseEntry> releaseEntries = new ArrayList<>();
            releaseEntries.add(new ReleaseEntry.Builder(orderReleaseId, ReleaseStatus.DL).shipmentSource(ShipmentSource.MYNTRA).build());
            ReleaseEntryList releaseEntryList= new ReleaseEntryList(releaseEntries);
            processRelease.processReleaseToStatus(releaseEntryList);
            lmsHelper.insertTestOrder(orderID, "DL", "DL");
            SlackMessenger.send("scm_e2e_order_sanity", "validating price and shipping cherges in OMS, LMS ans SPS");
            OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
            if( (orderEntry.getMrpTotal()+orderEntry.getShippingCharge()) != lmsServiceHelper.getLmsOrders(orderReleaseId).getOrders().get(0).getCodAmount())
            	SlackMessenger.send("scm_e2e_order_sanity", "`OMS-LMS Shipping charges validation failed`", COMPONENTS.OMS.ordinal());
            Assert.assertEquals(orderEntry.getMrpTotal()+orderEntry.getShippingCharge(),lmsServiceHelper.getLmsOrders(orderReleaseId).getOrders().get(0).getCodAmount(), "OMS-LMS Shipping charges validation failed");
            Assert.assertEquals(spsHelper.validateOrderStatusInSPS(orderID, "RELEASED",15), true, "Unable to validate order in SPS");

            SlackMessenger.send("scm_e2e_order_sanity", "Checking Email Notification");
            OrderEntry orderEntry2 = omsServiceHelper.getOrderEntry(orderID);
            
            boolean status2 = notificationServiceHelper.checkEmail(loginTestE2EMarkOrderDelivered, passwordTestE2EMarkOrderDelivered, TESTE2EMARKORDERDELIEVERED.ORDER_DELIVERED, DELIVERED.ORDER_NO,orderEntry2.getDisplayStoreOrderId().toString(), 5);
            if (!status2) {
                SlackMessenger.send("scm_e2e_order_sanity", "Email not present");
                Assert.fail("Email Notification Missing");
            }
            SlackMessenger.send("scm_e2e_order_sanity", "Email present");
            
        } catch (Exception ex) {
            SlackMessenger.send("scm_e2e_order_sanity", "`Error: " + ex.getMessage() + "`", COMPONENTS.OMS.ordinal());
            throw new Exception(ex);
        }
    }

    @Test(description = "End to End Create And Mark Order Delivered with price validation for Madura`",priority =2, enabled = false)
    public void testE2EMarkOrderDeliveredForMadura() throws Exception {
        OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
        LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
        SellerPaymentServiceHelper spsHelper = new SellerPaymentServiceHelper();
        try {
            SlackMessenger.send("scm_e2e_order_sanity", ">*`End to End Create And Mark Order Delivered For Madura`*");
            End2EndHelper end2EndHelper = new End2EndHelper();
            String orderID = end2EndHelper.createOrder(loginTestE2EMarkOrderDelivered, passwordTestE2EMarkOrderDelivered, pincode, new String[]{"895234:1"}, "", false, false, false, "", false);
            SlackMessenger.send("scm_e2e_order_sanity", "Order created " + orderID);
            String orderReleasId = omsServiceHelper.getReleaseId(orderID);
            boolean status = omsServiceHelper.validateReleaseStatusInOMS(orderReleasId, "WP", 15);
            if (!status) {
                SlackMessenger.send("scm_e2e_order_sanity", "`Order status is not WP for Order ID - " + orderReleasId + "`", COMPONENTS.OMS.ordinal());
                Assert.fail("Order Status is Not WP");
            }
            SlackMessenger.send("scm_e2e_order_sanity", "Order status WP");
            Assert.assertTrue(omsServiceHelper.validateOrderOFFHoldStatusInOMS(orderID, 15), "Unable to OFFHold status in OMS");
            List<ReleaseEntry> releaseEntries = new ArrayList<>();
            releaseEntries.add(new ReleaseEntry.Builder(orderReleasId, ReleaseStatus.DL).shipmentSource(ShipmentSource.MYNTRA).build());
            ReleaseEntryList releaseEntryList= new ReleaseEntryList(releaseEntries);
            processRelease.processReleaseToStatus(releaseEntryList);
            SlackMessenger.send("scm_e2e_order_sanity", "validating price and shipping cherges in OMS, LMS ans SPS");
            OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
            Assert.assertEquals(orderEntry.getMrpTotal()+orderEntry.getShippingCharge(),lmsServiceHelper.getLmsOrders(orderReleasId).getOrders().get(0).getCodAmount(), "OMS-LMS Shipping charges validation failed");
            Assert.assertEquals(spsHelper.validateOrderStatusInSPS(orderID, "RELEASED",15), true, "Unable to validate order in SPS");

        } catch (Exception ex) {
            SlackMessenger.send("scm_e2e_order_sanity", "`Error: " + ex.getMessage() + "`");
            throw new Exception(ex);
        }
    }

    @Test(description = "End to End Create And Mark Order Delivered with price validation for small seller`", priority =3, enabled = false)
    public void testE2EMarkOrderDeliveredForSmallSeller() throws Exception {
        OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
        LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
        SellerPaymentServiceHelper spsHelper = new SellerPaymentServiceHelper();
        try {
            SlackMessenger.send("scm_e2e_order_sanity", ">*`End to End Create And Mark Order Delivered for small seller`*");
            End2EndHelper end2EndHelper = new End2EndHelper();
            String orderID = end2EndHelper.createOrder(loginTestE2EMarkOrderDelivered, passwordTestE2EMarkOrderDelivered, pincode, new String[]{"1251868:1"}, "", false, false, false, "", false);
            SlackMessenger.send("scm_e2e_order_sanity", "Order created " + orderID);
            String orderReleaseId = omsServiceHelper.getReleaseId(orderID);
            boolean status = omsServiceHelper.validateReleaseStatusInOMS(orderReleaseId, "WP", 15);
            if (!status) {
                SlackMessenger.send("scm_e2e_order_sanity", "`Order status is not WP for Order ID - " + orderID + "`");
                Assert.fail("Order Status is Not WP");
            }
            SlackMessenger.send("scm_e2e_order_sanity", "Order status WP");
            Assert.assertTrue(omsServiceHelper.validateOrderOFFHoldStatusInOMS(orderID, 15), "Unable to OFFHold status in OMS");
            List<ReleaseEntry> releaseEntries = new ArrayList<>();
            releaseEntries.add(new ReleaseEntry.Builder(orderReleaseId, ReleaseStatus.DL).shipmentSource(ShipmentSource.MYNTRA).build());
            ReleaseEntryList releaseEntryList= new ReleaseEntryList(releaseEntries);
            processRelease.processReleaseToStatus(releaseEntryList);
            SlackMessenger.send("scm_e2e_order_sanity", "validating price and shipping charges in OMS, LMS ans SPS");
            OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
            Assert.assertEquals(orderEntry.getMrpTotal()+orderEntry.getShippingCharge(),lmsServiceHelper.getLmsOrders(orderReleaseId).getOrders().get(0).getCodAmount(), "OMS-LMS Shipping charges validation failed");
            Assert.assertEquals(spsHelper.validateOrderStatusInSPS(orderID, "RELEASED",15), true, "Unable to validate order in SPS");

        } catch (Exception ex) {
            SlackMessenger.send("scm_e2e_order_sanity", "`Error: " + ex.getMessage() + "`");
            throw new Exception(ex);
        }
    }

    @Deprecated
    @Test(description = "End to End Create, Deliver and Return Order", enabled = false)
    public void testE2EProcessReturn() throws Exception {
        OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
        try {
            SlackMessenger.send("scm_e2e_order_sanity", ">*`End to End Create, Deliver and Return Order`*");
            End2EndHelper end2EndHelper = new End2EndHelper();

            String orderID = end2EndHelper.createOrder(loginTestE2EProcessReturn, passwordTestE2EProcessReturn, pincode, new String[]{"1243744:1"}, "", false, false, false, "", false);
            SlackMessenger.send("scm_e2e_order_sanity", "Order created " + orderID);
            String orderReleaseId = omsServiceHelper.getReleaseId(orderID);
            boolean status = omsServiceHelper.validateReleaseStatusInOMS(orderReleaseId, "WP", 15);
            if (!status) {
                SlackMessenger.send("scm_e2e_order_sanity", "`Order status is not WP for Order ID - " + orderID + "`", COMPONENTS.OMS.ordinal());
                Assert.fail("Order Status is Not WP");
            }
            SlackMessenger.send("scm_e2e_order_sanity", "Order status WP");
            Assert.assertTrue(omsServiceHelper.validateOrderOFFHoldStatusInOMS(orderID, 15));
            List<ReleaseEntry> releaseEntries = new ArrayList<>();
            releaseEntries.add(new ReleaseEntry.Builder(orderReleaseId, ReleaseStatus.DL).shipmentSource(ShipmentSource.MYNTRA).build());
            ReleaseEntryList releaseEntryList= new ReleaseEntryList(releaseEntries);
            processRelease.processReleaseToStatus(releaseEntryList);
            com.myntra.oms.client.entry.OrderReleaseEntry orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(orderReleaseId);
            RMSServiceHelper rmsServiceHelper = new RMSServiceHelper();
            ReturnResponse returnResponse = rmsServiceHelper.createReturn(orderReleaseEntry.getOrderLines().get(0).getId().toString(), 1, ReturnMode.PICKUP, 27L);

            if (returnResponse.getStatus().getStatusType() != StatusResponse.Type.SUCCESS) {
                System.out.println("Return Creation failed - `" + returnResponse.getStatus().getStatusMessage() + "`");
                SlackMessenger.send("scm_e2e_order_sanity", "Return Creation failed - `" + returnResponse.getStatus().getStatusMessage() + "`", COMPONENTS.RMS.ordinal());
                Assert.fail("Return Creation failed");
            }
            String returnID = returnResponse.getData().get(0).getId().toString();
            SlackMessenger.send("scm_e2e_order_sanity", "Return Created - `" + returnResponse.getData().get(0).getId() + "`");
            end2EndHelper.completeOrderInRMS(returnID);
            
            if (rmsServiceHelper.validateReturnStatusInWMS(returnResponse.getData().get(0).getReturnLineEntries().get(0).getItemBarcode(), 3) == false) {
                System.out.println("Return not present in WMS ");
                SlackMessenger.send("scm_e2e_order_sanity", "Return not re-stocked in WMS - `");
                Assert.fail("Return not present in WMS");
            }
        } catch (Exception ex) {
            SlackMessenger.send("scm_e2e_order_sanity", "`Error: " + ex.getMessage() + "`");
            throw new Exception(ex);
        }
    }

    @Test(description = "End to End Create, Deliver and Exchange Order", priority =8, enabled = false)
    public void testE2EProcessExchange() throws Exception {
        OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
        LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
        SellerPaymentServiceHelper spsHelper = new SellerPaymentServiceHelper();
        LMSHelper lmsHelper = new LMSHelper();
        try {
            SlackMessenger.send("scm_e2e_order_sanity", ">*`End to End Create, Deliver and Exchange Order`*");
            End2EndHelper end2EndHelper = new End2EndHelper();
            PPSServiceHelper ppsServiceHelper = new PPSServiceHelper();

            String orderID = end2EndHelper.createOrder(loginTestE2EProcessExchange, passwordTestE2EProcessExchange, pincode, new String[]{"1243744:1"}, "", false, false, false, "", false);
            SlackMessenger.send("scm_e2e_order_sanity", "Order created " + orderID);
            String orderReleaseId = omsServiceHelper.getReleaseId(orderID);
            boolean status = omsServiceHelper.validateReleaseStatusInOMS(orderReleaseId, "WP", 15);
            if (!status) {
                SlackMessenger.send("scm_e2e_order_sanity", "`Order status is not WP for Order ID - " + orderID + "`");
                Assert.fail("Order Status is Not WP");
            }
            SlackMessenger.send("scm_e2e_order_sanity", "Order status WP");
            Assert.assertTrue(omsServiceHelper.validateOrderOFFHoldStatusInOMS(orderID, 15));
            System.out.println("Order ID : " + orderID);
            List<ReleaseEntry> releaseEntries = new ArrayList<>();
            releaseEntries.add(new ReleaseEntry.Builder(orderReleaseId, ReleaseStatus.DL).shipmentSource(ShipmentSource.MYNTRA).build());
            ReleaseEntryList releaseEntryList= new ReleaseEntryList(releaseEntries);
            processRelease.processReleaseToStatus(releaseEntryList);
            Assert.assertEquals(spsHelper.validateOrderStatusInSPS(orderID, "RELEASED",15), true, "Unable to validate order in SPS");
            com.myntra.oms.client.entry.OrderReleaseEntry orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(orderReleaseId);
            Long lineID = orderReleaseEntry.getOrderLines().get(0).getId();
            ExchangeOrderResponse exchangeOrderResponse = (ExchangeOrderResponse) ppsServiceHelper.createExchange("" + orderID, "" + lineID, "DNL", 1, "1243744");
            if (!exchangeOrderResponse.getSuccess()) {
                SlackMessenger.send("scm_e2e_order_sanity", "`Exchange creation failed for Order ID :" + orderID + "`", 1);
                Assert.fail("Exchange Creation failed");
            }
            String exchangeOrderID = omsServiceHelper.getOrderEntry(exchangeOrderResponse.getExchangeOrderId()).getId().toString();
            SlackMessenger.send("scm_e2e_order_sanity", "Exchange created Successfully, Exchanges Order ID :" + exchangeOrderID);
            List<ReleaseEntry> releaseEntries_Exe = new ArrayList<>();
            releaseEntries.add(new ReleaseEntry.Builder(exchangeOrderID, ReleaseStatus.DL).shipmentSource(ShipmentSource.MYNTRA).build());
            ReleaseEntryList releaseEntryList_Exe= new ReleaseEntryList(releaseEntries_Exe);
            processRelease.processReleaseToStatus(releaseEntryList_Exe);
            Assert.assertEquals(spsHelper.validateOrderStatusInSPS(orderID, "REFUNDED",15), true, "Unable to validate order in SPS");
            Assert.assertEquals(spsHelper.validateOrderStatusInSPS(exchangeOrderID, "RELEASED",15), true, "Unable to validate order in SPS");
            String returnID = lmsHelper.getReturnIdFromOrderId(""+orderReleaseId);
            SlackMessenger.send("scm_e2e_order_sanity", "Exchange returnID : " + returnID);
            Map<String, Object> pickup = DBUtilities.exSelectQueryForSingleRecord("select * from return_shipment where source_return_id = " + returnID, "lms");
            long destWarehouseId = (long) pickup.get("return_warehouse_id");
            lmsServiceHelper.transferShipmentBackToWH(returnID, destWarehouseId, 5, "DC", "WH");
            SlackMessenger.send("scm_e2e_order_sanity", "Exchange return is processed and sent back to configured warehouse");
        } catch (Exception ex) {
            SlackMessenger.send("scm_e2e_order_sanity", "Error: " + ex.getMessage());
            throw new Exception(ex);
        }
    }

    @Test(description = "End to End Create and RTO Order",priority =4, enabled = false)
    public void testE2EProcessRTO() throws Exception {
        OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
        try {
            SlackMessenger.send("scm_e2e_order_sanity", ">*`End to End Create and RTO Order`*");
            End2EndHelper end2EndHelper = new End2EndHelper();
            String orderID = end2EndHelper.createOrder(loginTestE2EProcessRTO, passwordTestE2EProcessRTO, pincode, new String[]{"1243744:1"}, "", false, false, false, "", false);
            SlackMessenger.send("scm_e2e_order_sanity", "Order created " + orderID);
            String orderReleaseId = omsServiceHelper.getReleaseId(orderID);
            boolean status = omsServiceHelper.validateReleaseStatusInOMS(orderReleaseId, "WP", 15);
            if (!status) {
                SlackMessenger.send("scm_e2e_order_sanity", "`Order status is not WP for Order ID - " + orderID + "`", COMPONENTS.OMS.ordinal());
                Assert.fail("Order Status is Not WP");
            }
            SlackMessenger.send("scm_e2e_order_sanity", "Order status WP");
            Assert.assertTrue(omsServiceHelper.validateOrderOFFHoldStatusInOMS(orderID, 15));
            System.out.println("Order ID : " + orderID);
            List<ReleaseEntry> releaseEntries = new ArrayList<>();
            releaseEntries.add(new ReleaseEntry.Builder(orderReleaseId, ReleaseStatus.RTO).shipmentSource(ShipmentSource.MYNTRA).build());
            ReleaseEntryList releaseEntryList= new ReleaseEntryList(releaseEntries);
            processRelease.processReleaseToStatus(releaseEntryList);
            status = omsServiceHelper.validateReleaseStatusInOMS(orderReleaseId, "RTO", 15);
            if (!status) {
                SlackMessenger.send("scm_e2e_order_sanity", "`Order status is not RTO in OMS for Order ID - " + orderID + "`", COMPONENTS.OMS.ordinal());
                Assert.fail("Order Status is not RTO in OMS");
            }
        } catch (Exception ex) {
            SlackMessenger.send("scm_e2e_order_sanity", "`Error: " + ex.getMessage() + "`");
            throw new Exception(ex);
        }
    }

    @Test(description = "End to End Create and Lost Order", priority =5, enabled = false)
    public void testE2EProcessLost() throws Exception {
        OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
        try {
            SlackMessenger.send("scm_e2e_order_sanity", ">*`End to End Create and Lost Order`*");
            End2EndHelper end2EndHelper = new End2EndHelper();
            String orderID = end2EndHelper.createOrder(loginTestE2EProcessLost, passwordTestE2EProcessLost, pincode, new String[]{"1243744:1"}, "", false, false, false, "", false);
            SlackMessenger.send("scm_e2e_order_sanity", "Order created " + orderID);
            String orderReleaseId = omsServiceHelper.getReleaseId(orderID);
            boolean status = omsServiceHelper.validateReleaseStatusInOMS(orderReleaseId, "WP", 15);
            if (!status) {
                SlackMessenger.send("scm_e2e_order_sanity", "`Order status is not WP for Order ID - " + orderID + "`", COMPONENTS.OMS.ordinal());
                Assert.fail("Order Processing failed as order status is not WP");
            }
            SlackMessenger.send("scm_e2e_order_sanity", "Order status WP");
            Assert.assertTrue(omsServiceHelper.validateOrderOFFHoldStatusInOMS(orderID, 15));
            List<ReleaseEntry> releaseEntries = new ArrayList<>();
            releaseEntries.add(new ReleaseEntry.Builder(orderReleaseId, ReleaseStatus.LOST).shipmentSource(ShipmentSource.MYNTRA).build());
            ReleaseEntryList releaseEntryList= new ReleaseEntryList(releaseEntries);
            processRelease.processReleaseToStatus(releaseEntryList);
            status = omsServiceHelper.validateReleaseStatusInOMS(orderReleaseId, "L", 15);
            if (!status) {
                SlackMessenger.send("scm_e2e_order_sanity", "`Order status is not Lost in OMS for Order ID : " + orderID + "`", COMPONENTS.OMS.ordinal());
                Assert.fail("Order Processing failed as order status is not L in OMS");
            }

        } catch (Exception ex) {
            SlackMessenger.send("scm_e2e_order_sanity", "`Error: " + ex.getMessage() + "`");
            throw new Exception(ex);
        }
    }

    @Test(description = "End to End Create and return with PICKUP_SUCCESSFUL_QC_PENDING_APPROVED", priority =6, enabled = false)
    public void processReturnThroughLMSFlow() throws Exception {
        OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
        RMSServiceHelper rmsServiceHelper = new RMSServiceHelper();
        LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
        SellerPaymentServiceHelper spsHelper = new SellerPaymentServiceHelper();
        String orderID;
        String orderReleaseId;
        try {
            SlackMessenger.send("scm_e2e_order_sanity", ">*`End to End Create and return with PICKUP_SUCCESSFUL_QC_PENDING_APPROVED`*");
            End2EndHelper end2EndHelper = new End2EndHelper();
            orderID = end2EndHelper.createOrder(loginProcessReturnThroughLMSFlow, passwordProcessReturnThroughLMSFlow, pincode, new String[]{"1243744:1"}, "", false, false, false, "", false);
            SlackMessenger.send("scm_e2e_order_sanity", "Order created " + orderID);
            orderReleaseId = omsServiceHelper.getReleaseId(orderID);
            boolean status = omsServiceHelper.validateReleaseStatusInOMS(orderReleaseId, "WP", 15);
            if (!status) {
                SlackMessenger.send("scm_e2e_order_sanity", "`Order status is not WP for Order ID - " + orderID + "`", COMPONENTS.OMS.ordinal());
                Assert.fail("Order Processing failed as order status is not WP");
            }
            SlackMessenger.send("scm_e2e_order_sanity", "Order status WP");
            Assert.assertTrue(omsServiceHelper.validateOrderOFFHoldStatusInOMS(orderID, 15));
            List<ReleaseEntry> releaseEntries = new ArrayList<>();
            releaseEntries.add(new ReleaseEntry.Builder(orderReleaseId, ReleaseStatus.DL).shipmentSource(ShipmentSource.MYNTRA).build());
            ReleaseEntryList releaseEntryList= new ReleaseEntryList(releaseEntries);
            processRelease.processReleaseToStatus(releaseEntryList);
            status = omsServiceHelper.validateReleaseStatusInOMS(orderReleaseId, "DL", 15);
            if (!status) {
                SlackMessenger.send("scm_e2e_order_sanity", "`Order status is not DL in OMS for Order ID : " + orderID + "`", COMPONENTS.OMS.ordinal());
                Assert.fail("Order Processing failed as order status is not DL in OMS");
            }
            Assert.assertEquals(spsHelper.validateOrderStatusInSPS(orderID, "RELEASED",15), true, "Unable to validate order in SPS");
        } catch (Exception ex) {
            SlackMessenger.send("scm_e2e_order_sanity", "`Error: " + ex.getMessage() + "`");
            throw new Exception(ex);
        }
        try {

            com.myntra.oms.client.entry.OrderReleaseEntry orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(orderReleaseId);
            ReturnResponse returnResponse = rmsServiceHelper.createReturn(orderReleaseEntry.getOrderLines().get(0).getId().toString(), 1, ReturnMode.PICKUP, 27L, RefundMode.NEFT, "1234567890", "Myntra test lms automation", "Bangalore", "KA", "560068", "ML");
            if (!returnResponse.getStatus().getStatusType().toString().equals("SUCCESS")) {
                SlackMessenger.send("scm_e2e_order_sanity", "`Unable to create return`");
              Assert.fail("Unable to create return");
            }
            SlackMessenger.send("scm_e2e_order_sanity", "`Return created, returnID: " + returnResponse.getData().get(0).getId() + "`. Now checking in LMS for pickup creation");
            Long returnID = returnResponse.getData().get(0).getId();
            lms_returnHelper.validatePickupShipmentStatusInLMS(""+returnID, "PICKUP_CREATED", 15);
            SlackMessenger.send("scm_e2e_order_sanity", "`Pickup created in LMS`");
            lmsServiceHelper.processReturnInLMS(""+returnID, "PICKED_UP_QCP_APPROVED_Before_trip_close");
            SlackMessenger.send("scm_e2e_order_sanity", "`Pickup successfully processed`");
            if(!spsHelper.validateOrderStatusInSPS(orderID, "REFUNDED",15))
            	SlackMessenger.send("scm_e2e_order_sanity", "`Unable to validate order in SPS`", 5);
            Assert.assertEquals(spsHelper.validateOrderStatusInSPS(orderID, "REFUNDED",15), true, "Unable to validate order in SPS");
        } catch (Exception ex) {
            SlackMessenger.send("scm_e2e_order_sanity", "`Error: " + ex.getMessage() + "`");
            throw new Exception(ex);
        }
    }

    @Test(description = "End to End Create And Mark TOD_Order Delivered with TRIED_AND_BOUGHT`", priority =7, enabled = false)
    public void testE2EMarkTODOrderDelivered() throws Exception {
        OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
//        SellerPaymentServiceHelper spsHelper = new SellerPaymentServiceHelper();
        try {
            SlackMessenger.send("scm_e2e_order_sanity", ">*`End to End Create And Mark TOD_Order Delivered`*");
            End2EndHelper end2EndHelper = new End2EndHelper();
            String orderID = end2EndHelper.createOrder(loginTestE2EMarkTODOrderDelivered, passwordTestE2EMarkTODOrderDelivered, pincode, new String[]{"1243744:1", "3913:1"}, "", false, false, false, "", true);
            SlackMessenger.send("scm_e2e_order_sanity", "Order created " + orderID);
            String orderReleaseId = omsServiceHelper.getReleaseId(orderID);
            boolean status = omsServiceHelper.validateReleaseStatusInOMS(orderReleaseId, "WP", 15);
            if (!status) {
                SlackMessenger.send("scm_e2e_order_sanity", "`Order status is not WP for Order ID - " + orderID + "`", COMPONENTS.OMS.ordinal());
                Assert.fail("Order Status is Not WP");
            }
            SlackMessenger.send("scm_e2e_order_sanity", "Order status WP");
            if(!omsServiceHelper.validateOrderOFFHoldStatusInOMS(orderID, 15))
            	SlackMessenger.send("scm_e2e_order_sanity", "`Validate OrderStatus in OMS Order_release table - " + orderID + "`", COMPONENTS.OMS.ordinal());
            Assert.assertTrue(omsServiceHelper.validateOrderOFFHoldStatusInOMS(orderID, 15));
            System.out.println("Order ID : " + orderID);
            HashMap<String, String> skuAndStatus = new HashMap<>();
            skuAndStatus.put("1243744", EnumSCM.TRIED_AND_BOUGHT);
            skuAndStatus.put("3913", EnumSCM.TRIED_AND_NOT_BOUGHT);
            List<ReleaseEntry> releaseEntries = new ArrayList<>();
            releaseEntries.add(new ReleaseEntry.Builder(orderReleaseId, ReleaseStatus.DL).shipmentSource(ShipmentSource.MYNTRA).build());
            ReleaseEntryList releaseEntryList= new ReleaseEntryList(releaseEntries);
            processRelease.processReleaseToStatus(releaseEntryList);
            SlackMessenger.send("scm_e2e_order_sanity", "Order marked TRIED_AND_BOUGHT");
//            if(!spsHelper.validateOrderStatusInSPS(orderID, "RELEASED",15))
//            	SlackMessenger.send("scm_e2e_order_sanity", "`Unable to validate order in SPS`", 5);
//            Assert.assertEquals(spsHelper.validateOrderStatusInSPS(orderID, "RELEASED",15), true, "Unable to validate order in SPS");
        } catch (Exception ex) {
            SlackMessenger.send("scm_e2e_order_sanity", "`Error: " + ex.getMessage() + "`");
            throw new Exception(ex);
        }
    }



/*    @Test(description = "End to End Create And Mark TOD_Order Delivered with TRIED_AND_BOUGHT`")
    public void testCreateEGCOrder() throws Exception {
        String skuId[] = { "11024406:1" };
        OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
        try {
            SlackMessenger.send("scm_e2e_order_sanity", ">*`End to End Create EGC Order And Process till Completed State`*");
            End2EndHelper end2EndHelper = new End2EndHelper();
            long orderID = end2EndHelper.createOrderWithEGiftCard(loginTestCreateEGCOrder, passwordTestCreateEGCOrder, pincode,skuId, "EndToEnd","Good Luck!","sasasasas","abhijit.pati@myntra.com");
            boolean status = omsServiceHelper.validateReleaseStatusInOMS(orderID, "DL", 15);
            if (!status) {
                SlackMessenger.send("scm_e2e_order_sanity", "`Order status is not WP for Order ID - " + orderID + "`");
                Assert.fail("Order Status is Not DL");
            }
            SlackMessenger.send("scm_e2e_order_sanity", "EGC Order status is in DL State");
        } catch (Exception ex) {
            SlackMessenger.send("scm_e2e_order_sanity", "`Error: " + ex.getMessage() + "`");
            throw new Exception(ex);
        }

    }*/


}