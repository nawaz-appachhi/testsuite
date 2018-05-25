package com.myntra.qnotifications;

import com.myntra.apiTests.common.Constants.ReleaseStatus;
import com.myntra.apiTests.common.ProcessOrder.Service.ProcessRelease;
import com.myntra.apiTests.common.entries.ReleaseEntry;
import com.myntra.apiTests.common.entries.ReleaseEntryList;
import com.myntra.apiTests.end2end.End2EndHelper;
import com.myntra.apiTests.erpservices.lms.Constants.PaymentMode;
import com.myntra.apiTests.erpservices.lms.Helper.LMSHelper;
import com.myntra.apiTests.erpservices.lms.Helper.LmsServiceHelper;
import com.myntra.apiTests.erpservices.notificaton.NotificationServiceHelper;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.erpservices.rms.RMSServiceHelper;
import com.myntra.apiTests.erpservices.rms.dp.RmsDP;
import com.myntra.apiTests.portalservices.pps.PPSServiceHelper;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.lordoftherings.parser.emailParser.Email;
import com.myntra.lordoftherings.parser.emailParser.ParseEmail;
import com.myntra.lordoftherings.parser.emailParser.XMLParser;
import com.myntra.oms.client.entry.OrderEntry;
import com.myntra.oms.client.entry.OrderLineEntry;
import com.myntra.oms.client.entry.OrderReleaseEntry;
import com.myntra.paymentplan.domain.response.ExchangeOrderResponse;
import com.myntra.qnotifications.Xpath_Constants.*;
import com.myntra.returns.common.enums.ReturnMode;
import com.myntra.returns.common.enums.code.ReturnLineStatus;
import com.myntra.returns.common.enums.code.ReturnStatus;
import com.myntra.returns.entry.ReturnEntry;
import com.myntra.returns.entry.ReturnLineEntry;
import com.myntra.returns.response.ReturnLineResponse;
import com.myntra.returns.response.ReturnResponse;
import com.myntra.test.commons.testbase.BaseTest;
import org.jsoup.Jsoup;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EndToEndNotifications extends BaseTest{

	static Initialize init = new Initialize("/Data/configuration");
	static org.slf4j.Logger log = LoggerFactory.getLogger(EndToEndNotifications.class);
	NotificationServiceHelper notifyHelper = new NotificationServiceHelper();
	End2EndHelper e2e = new End2EndHelper();
	LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
	OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
	RMSServiceHelper rmsServiceHelper = new RMSServiceHelper();
	PPSServiceHelper ppsServiceHelper = new PPSServiceHelper();
	End2EndHelper end2EndHelper = new End2EndHelper();
	LMSHelper lmsHepler = new LMSHelper();
	ProcessRelease processRelease = new ProcessRelease();
	private String login = "end2endautomation4@myntra.com";

	private String BLRAddressId = "6136170";
	Notifications_validation notif = new Notifications_validation();
	ParseEmail parse = new ParseEmail();
	Email email = new Email();
	XMLParser read = new XMLParser();
	final String emailId = "end2endautomation4@gmail.com";
	final String password = "myntra@123";
	final String mobilenumber = "9823888800";
	final String uidx = "b268393b.68e2.462e.bb86.8c6e3fa0086erCrD3tMS94";


	//Triggers order confirmation email & SMS and checks the contents
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression", "ExhaustiveRegression","RMSService" },
			enabled=true,priority=1, description = "Triggers order confirmation email and checks the contents")

	public void verifyOrderConfirmed() throws Exception {
		notifyHelper.deleteallSMSRecords(mobilenumber);
		notif.login_delete_emails(emailId, password);
		String orderID = e2e.createOrder(emailId, password, BLRAddressId, new String[] {ORDER_CONFIRM.SKU_QUANTITY}, "", false, false, false, "", false);
		OMSServiceHelper omsHelper = new OMSServiceHelper();
		OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
		OrderReleaseEntry releaseEntry = omsServiceHelper.getOrderReleaseEntry(omsServiceHelper.getReleaseId(orderEntry.getId().toString()));
		OrderLineEntry lineEntry = omsHelper.getOrderLineEntry(releaseEntry.getOrderLines().get(0).getId().toString());
		//email.login(emailId, password);
		Thread.sleep(30000);
		email.login(emailId, password);
		String html = email.fetchContent("Your Myntra Order Confirmation");
		log.info("Extracted html"+html);
		//Assert.assertTrue(notifyHelper.WaitCheckEmail("Your Myntra Order confirmation ", email, 10));
		//String html = email.fetchContent("Your Myntra Order confirmation ");
		Assert.assertTrue(notifyHelper.getParsedData(html,ORDER_CONFIRM.NAME).contains(returnTrimmedString(releaseEntry.getReceiverName())));
		log.info("\n Name :" +notifyHelper.getParsedData(html,ORDER_CONFIRM.NAME));

		Assert.assertTrue(notifyHelper.getParsedData(html,ORDER_CONFIRM.ORDER_ID).contains(returnTrimmedString(releaseEntry.getDisplayStoreOrderId().toString())));
		log.info("\n Order Id :" +notifyHelper.getParsedData(html,ORDER_CONFIRM.ORDER_ID));

		Assert.assertTrue(notifyHelper.getParsedData(html,ORDER_CONFIRM.AMOUNT).contains(returnTrimmedString(lineEntry.getFinalAmount().toString())));
		log.info("\n Amount :" +notifyHelper.getParsedData(html,ORDER_CONFIRM.AMOUNT));

		Assert.assertTrue(notifyHelper.getParsedData(html,ORDER_CONFIRM.TOTAL_ITEMS).contains(returnTrimmedString(releaseEntry.getOrderLines().get(0).getQuantity().toString())));
		log.info("\n Total Items :" +notifyHelper.getParsedData(html,ORDER_CONFIRM.TOTAL_ITEMS));

		Assert.assertTrue(notifyHelper.getParsedData(html,ORDER_CONFIRM.PRODUCT_NAME).contains(returnTrimmedString(ORDER_CONFIRM.STYLE)));
		log.info("\n Product Name :" +notifyHelper.getParsedData(html,ORDER_CONFIRM.PRODUCT_NAME));

		Assert.assertTrue(notifyHelper.getParsedData(html,ORDER_CONFIRM.PRODUCT_DETAILS).contains(returnTrimmedString("Size: M")));
		Assert.assertTrue(notifyHelper.getParsedData(html,ORDER_CONFIRM.PRODUCT_DETAILS).contains(returnTrimmedString(releaseEntry.getOrderLines().get(0).getQuantity().toString())));
		Assert.assertTrue(notifyHelper.getParsedData(html,ORDER_CONFIRM.PRODUCT_DETAILS).contains(returnTrimmedString(lineEntry.getFinalAmount().toString())));
		log.info("\n Product Detail :" +notifyHelper.getParsedData(html,ORDER_CONFIRM.PRODUCT_DETAILS));

		Assert.assertTrue(notifyHelper.getParsedData(html,ORDER_CONFIRM.ADDRESS).contains(returnTrimmedString(releaseEntry.getReceiverName())));
		Assert.assertTrue(notifyHelper.getParsedData(html,ORDER_CONFIRM.ADDRESS).contains(returnTrimmedString(releaseEntry.getAddress())));
		Assert.assertTrue(notifyHelper.getParsedData(html,ORDER_CONFIRM.ADDRESS).contains(returnTrimmedString(releaseEntry.getCity())));
		Assert.assertTrue(notifyHelper.getParsedData(html,ORDER_CONFIRM.ADDRESS).contains(returnTrimmedString(releaseEntry.getZipcode())));
		Assert.assertTrue(notifyHelper.getParsedData(html,ORDER_CONFIRM.ADDRESS).contains(returnTrimmedString(releaseEntry.getState())));
		Assert.assertTrue(notifyHelper.getParsedData(html,ORDER_CONFIRM.ADDRESS).contains(returnTrimmedString(releaseEntry.getMobile())));
		log.info("\n Address :" +notifyHelper.getParsedData(html,ORDER_CONFIRM.ADDRESS));

		Assert.assertTrue(notifyHelper.getParsedData(html,ORDER_CONFIRM.ORDER_VALUE).contains(returnTrimmedString(lineEntry.getFinalAmount().toString())));
		log.info("\n Order Value :" +notifyHelper.getParsedData(html,ORDER_CONFIRM.ORDER_VALUE));

		Assert.assertTrue(notifyHelper.getParsedData(html,ORDER_CONFIRM.SHIPPING_CHARGE).contains(returnTrimmedString(releaseEntry.getShippingCharge().toString())));
		log.info("\n Shipping Charge :" +notifyHelper.getParsedData(html,ORDER_CONFIRM.SHIPPING_CHARGE));

		Assert.assertTrue(notifyHelper.getParsedData(html,ORDER_CONFIRM.TOTAL).contains(returnTrimmedString(releaseEntry.getFinalAmount().toString())));
		log.info("\n Total :" +notifyHelper.getParsedData(html,ORDER_CONFIRM.TOTAL));

		Assert.assertFalse(Jsoup.parse(html).text().contains("$"));

		XMLParser.readAllLinks(html);

		//Bounty SMS
		log.info("\n Bounty SMS :" +notifyHelper.getSMS(mobilenumber,ORDER_CONFIRM.BOUNTY_SMS_ID));
		Assert.assertNotNull(notifyHelper.getSMS(mobilenumber,ORDER_CONFIRM.BOUNTY_SMS_ID));
		Assert.assertTrue(notifyHelper.getSMS(mobilenumber,ORDER_CONFIRM.BOUNTY_SMS_ID).contains(returnTrimmedString(releaseEntry.getDisplayStoreOrderId().toString())));
		Assert.assertTrue(notifyHelper.getSMS(mobilenumber,ORDER_CONFIRM.BOUNTY_SMS_ID).toLowerCase().contains(returnTrimmedString(releaseEntry.getReceiverName())));

		//Order Confirmation SMS
		log.info("\n Order Confirmation SMS :" +notifyHelper.getSMS(mobilenumber,ORDER_CONFIRM.ORDERCONFIRM_SMS_ID));
		Assert.assertNotNull(notifyHelper.getSMS(mobilenumber,ORDER_CONFIRM.ORDERCONFIRM_SMS_ID));
		Assert.assertTrue(notifyHelper.getSMS(mobilenumber,ORDER_CONFIRM.ORDERCONFIRM_SMS_ID).contains(returnTrimmedString(releaseEntry.getDisplayStoreOrderId().toString())));
		Assert.assertTrue(notifyHelper.getSMS(mobilenumber,ORDER_CONFIRM.ORDERCONFIRM_SMS_ID).toLowerCase().contains(returnTrimmedString(releaseEntry.getReceiverName())));
		Assert.assertFalse(notifyHelper.getSMS(mobilenumber,ORDER_CONFIRM.ORDERCONFIRM_SMS_ID).contains("$"));


	}




	//Triggers order dispatched email & sms and checks the contents
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression", "ExhaustiveRegression","RMSService" },
			enabled=true,priority=2,description = "Triggers order dispatched email and checks the contents")

	public void verifyOrderDispatched() throws Exception {

		notif.login_delete_emails(emailId, password);
		notifyHelper.deleteallSMSRecords(mobilenumber);
		String orderID = e2e.createOrder(emailId, password, BLRAddressId, new String[] {ORDER_DISPATCHED.SKU_QUANTITY}, "", false, false, false, "", false);
		Thread.sleep(10000);


		List<ReleaseEntry> releaseEntries = new ArrayList<>();
		releaseEntries.add(new ReleaseEntry.Builder(omsServiceHelper.getReleaseId(orderID), ReleaseStatus.DL).build());
		ReleaseEntryList releaseEntryList= new ReleaseEntryList(releaseEntries);
		processRelease.processReleaseToStatus(releaseEntryList);

		//processRelease.processReleaseToStatus(new ReleaseEntry.Builder(omsServiceHelper.getReleaseId(orderID), ReleaseStatus.DL).build());
		Thread.sleep(20000);
		email.login(emailId, password);
		String html = email.fetchContent("Your Myntra order has been dispatched from the warehouse");

		OMSServiceHelper omsHelper = new OMSServiceHelper();
		OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
		OrderReleaseEntry releaseEntry = omsServiceHelper.getOrderReleaseEntry(omsServiceHelper.getReleaseId(orderEntry.getId().toString()));
		OrderLineEntry lineEntry = omsHelper.getOrderLineEntry(releaseEntry.getOrderLines().get(0).getId().toString());

		Assert.assertTrue(notifyHelper.getParsedData(html,ORDER_DISPATCHED.NAME).contains(returnTrimmedString(releaseEntry.getReceiverName())));
		log.info("\n Name :" +notifyHelper.getParsedData(html,ORDER_DISPATCHED.NAME));

		Assert.assertNotNull(notifyHelper.getParsedData(html,ORDER_DISPATCHED.SHIPMENT_DETAIL));
		log.info("\n Shipment Detail :" +notifyHelper.getParsedData(html,ORDER_DISPATCHED.SHIPMENT_DETAIL));

		Assert.assertTrue(notifyHelper.getParsedData(html,ORDER_DISPATCHED.SHIPMENT_DETAIL).contains(returnTrimmedString(releaseEntry.getDisplayStoreOrderId())));
		log.info("\n Name :" +notifyHelper.getParsedData(html,ORDER_DISPATCHED.SHIPMENT_DETAIL));

		Assert.assertEquals(notifyHelper.getParsedData(html,ORDER_DISPATCHED.PRODUCT_NAME),ORDER_DISPATCHED.STYLE);
		log.info("\n Product Name :" +notifyHelper.getParsedData(html,ORDER_DISPATCHED.PRODUCT_NAME));

		Assert.assertNotNull(notifyHelper.getParsedData(html,ORDER_DISPATCHED.PRODUCT_DETAILS));
		log.info("\n Product Details :" +notifyHelper.getParsedData(html,ORDER_DISPATCHED.PRODUCT_DETAILS));

		Assert.assertTrue(notifyHelper.getParsedData(html,ORDER_DISPATCHED.PACKAGE_VALUE).contains(returnTrimmedString(lineEntry.getFinalAmount().toString())));
		log.info("\n Product Name :" +notifyHelper.getParsedData(html,ORDER_DISPATCHED.PACKAGE_VALUE));

		Assert.assertEquals(notifyHelper.getParsedData(html,ORDER_DISPATCHED.SHIPPING_CHARGE),"free");
		log.info("\n Shipping Charge  :" +notifyHelper.getParsedData(html,ORDER_DISPATCHED.SHIPPING_CHARGE));

		Assert.assertTrue(notifyHelper.getParsedData(html,ORDER_DISPATCHED.ORDER_VALUE).contains(returnTrimmedString(releaseEntry.getFinalAmount().toString())));
		log.info("\n Order Value :" +notifyHelper.getParsedData(html,ORDER_DISPATCHED.ORDER_VALUE));

		Assert.assertFalse(Jsoup.parse(html).text().contains("$"));
		XMLParser.readAllLinks(html);
		//Order Dispatched SMS
		log.info("\n Order Dispatched SMS :" +notifyHelper.getSMS(mobilenumber,ORDER_DISPATCHED.ORDERDISPATCHED_SMS_ID));
		Assert.assertNotNull(notifyHelper.getSMS(mobilenumber,ORDER_DISPATCHED.ORDERDISPATCHED_SMS_ID));
		Assert.assertTrue(notifyHelper.getSMS(mobilenumber,ORDER_DISPATCHED.ORDERDISPATCHED_SMS_ID).contains(returnTrimmedString(releaseEntry.getDisplayStoreOrderId())));
		Assert.assertFalse(notifyHelper.getSMS(mobilenumber,ORDER_DISPATCHED.ORDERDISPATCHED_SMS_ID).contains("$"));
	}

	//Triggers out for delivery email & sms and checks the contents
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression", "ExhaustiveRegression","RMSService" },
			enabled=true,priority=3,description = "Triggers out for delivery email and checks the contents")
	public void verifyOutForDelivery() throws Exception {

		notif.login_delete_emails(emailId, password);
		notifyHelper.deleteallSMSRecords(mobilenumber);
		String orderID = e2e.createOrder(emailId, password, BLRAddressId, new String[] {OUT_FOR_DELIVERY.SKU_QUANTITY}, "", false, false, false, "", false);
		Thread.sleep(10000);

		List<ReleaseEntry> releaseEntries = new ArrayList<>();
		releaseEntries.add(new ReleaseEntry.Builder(omsServiceHelper.getReleaseId(orderID), ReleaseStatus.DL).build());
		ReleaseEntryList releaseEntryList= new ReleaseEntryList(releaseEntries);
		processRelease.processReleaseToStatus(releaseEntryList);


		//processRelease.processReleaseToStatus(new ReleaseEntry.Builder(omsServiceHelper.getReleaseId(orderID), ReleaseStatus.DL).build());
		Thread.sleep(20000);
		email.login(emailId, password);
		String html = email.fetchContent("Your Myntra shipment is out for delivery");

		OMSServiceHelper omsHelper = new OMSServiceHelper();
		OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
		OrderReleaseEntry releaseEntry = omsServiceHelper.getOrderReleaseEntry(omsServiceHelper.getReleaseId(orderEntry.getId().toString()));
		OrderLineEntry lineEntry = omsHelper.getOrderLineEntry(releaseEntry.getOrderLines().get(0).getId().toString());

		Assert.assertEquals(notifyHelper.getParsedData(html,OUT_FOR_DELIVERY.NAME),"hi test");
		log.info("\n Name :" +notifyHelper.getParsedData(html,OUT_FOR_DELIVERY.NAME));

		Assert.assertNotNull(notifyHelper.getParsedData(html,OUT_FOR_DELIVERY.SHIPMENT_DETAIL));
		log.info("\n Shipment Detail :" +notifyHelper.getParsedData(html,OUT_FOR_DELIVERY.SHIPMENT_DETAIL));

		Assert.assertTrue(notifyHelper.getParsedData(html,OUT_FOR_DELIVERY.SHIPMENT_DETAIL).contains(returnTrimmedString(releaseEntry.getDisplayStoreOrderId())));
		log.info("\n Name :" +notifyHelper.getParsedData(html,OUT_FOR_DELIVERY.SHIPMENT_DETAIL));

		Assert.assertEquals(notifyHelper.getParsedData(html,OUT_FOR_DELIVERY.PRODUCT_NAME),OUT_FOR_DELIVERY.STYLE);
		log.info("\n Product Name :" +notifyHelper.getParsedData(html,OUT_FOR_DELIVERY.PRODUCT_NAME));

		Assert.assertNotNull(notifyHelper.getParsedData(html,OUT_FOR_DELIVERY.PRODUCT_DETAILS));
		log.info("\n Product Details :" +notifyHelper.getParsedData(html,OUT_FOR_DELIVERY.PRODUCT_DETAILS));

		Assert.assertNotNull(notifyHelper.getParsedData(html,OUT_FOR_DELIVERY.ADDRESS));
		log.info("\n Address :" +notifyHelper.getParsedData(html,OUT_FOR_DELIVERY.ADDRESS));

		Assert.assertTrue(notifyHelper.getParsedData(html,OUT_FOR_DELIVERY.PACKAGE_VALUE).contains(returnTrimmedString(lineEntry.getFinalAmount().toString())));
		log.info("\n Package Value :" +notifyHelper.getParsedData(html,OUT_FOR_DELIVERY.PACKAGE_VALUE));

		Assert.assertEquals(notifyHelper.getParsedData(html,OUT_FOR_DELIVERY.SHIPPING_CHARGE),"free");
		log.info("\n Shipping Charge  :" +notifyHelper.getParsedData(html,OUT_FOR_DELIVERY.SHIPPING_CHARGE));

		Assert.assertTrue(notifyHelper.getParsedData(html,OUT_FOR_DELIVERY.ORDER_VALUE).contains(returnTrimmedString(releaseEntry.getFinalAmount().toString())));
		log.info("\n Order Value :" +notifyHelper.getParsedData(html,OUT_FOR_DELIVERY.ORDER_VALUE));

		Assert.assertFalse(Jsoup.parse(html).text().contains("$"));

		//Order Out for Delivery SMS
		log.info("\n Order Dispatched SMS :" +notifyHelper.getSMS(mobilenumber,OUT_FOR_DELIVERY.OUTFORDELIVERY_SMS_ID));
		Assert.assertNotNull(notifyHelper.getSMS(mobilenumber,OUT_FOR_DELIVERY.OUTFORDELIVERY_SMS_ID));
		Assert.assertTrue(notifyHelper.getSMS(mobilenumber,OUT_FOR_DELIVERY.OUTFORDELIVERY_SMS_ID).contains(returnTrimmedString(releaseEntry.getDisplayStoreOrderId())));
		Assert.assertTrue(notifyHelper.getSMS(mobilenumber,OUT_FOR_DELIVERY.OUTFORDELIVERY_SMS_ID).contains(returnTrimmedString(releaseEntry.getId().toString())));
		Assert.assertTrue(notifyHelper.getSMS(mobilenumber,OUT_FOR_DELIVERY.OUTFORDELIVERY_SMS_ID).toLowerCase().contains(returnTrimmedString(releaseEntry.getReceiverName())));
		Assert.assertTrue(notifyHelper.getSMS(mobilenumber,OUT_FOR_DELIVERY.OUTFORDELIVERY_SMS_ID).toLowerCase().contains(returnTrimmedString(releaseEntry.getFinalAmount().toString())));
		Assert.assertFalse(notifyHelper.getSMS(mobilenumber,OUT_FOR_DELIVERY.OUTFORDELIVERY_SMS_ID).contains("$"));

	}

	//Triggers delivered email & sms and checks the contents
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression", "ExhaustiveRegression","RMSService" },
			enabled=true,priority=4,description = "Triggers delivered email and checks the contents")
	public void verifyDelivered() throws Exception {

		notif.login_delete_emails(emailId, password);
		notifyHelper.deleteallSMSRecords(mobilenumber);
		String orderID = e2e.createOrder(emailId, password, BLRAddressId, new String[] {DELIVERED.SKU_QUANTITY}, "", false, false, false, "", false);

		Thread.sleep(10000);
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");

		List<ReleaseEntry> releaseEntries = new ArrayList<>();
		releaseEntries.add(new ReleaseEntry.Builder(omsServiceHelper.getReleaseId(orderID), ReleaseStatus.DL).build());
		ReleaseEntryList releaseEntryList= new ReleaseEntryList(releaseEntries);
		processRelease.processReleaseToStatus(releaseEntryList);

		//processRelease.processReleaseToStatus(new ReleaseEntry.Builder(omsServiceHelper.getReleaseId(orderID), ReleaseStatus.DL).build());
		Thread.sleep(20000);
		email.login(emailId, password);
		String html = email.fetchContent("Your Myntra shipment has been delivered");

		OMSServiceHelper omsHelper = new OMSServiceHelper();
		OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
		OrderReleaseEntry releaseEntry = omsServiceHelper.getOrderReleaseEntry(omsServiceHelper.getReleaseId(orderEntry.getId().toString()));
		OrderLineEntry lineEntry = omsHelper.getOrderLineEntry(releaseEntry.getOrderLines().get(0).getId().toString());


		Assert.assertTrue(notifyHelper.getParsedData(html,DELIVERED.NAME).contains(returnTrimmedString(releaseEntry.getReceiverName())));
		log.info("\n Name :" +notifyHelper.getParsedData(html,DELIVERED.NAME));

		Assert.assertEquals(notifyHelper.getParsedData(html,DELIVERED.MESSAGE),DELIVERED.MESSAGE2);
		log.info("\n Message :" +notifyHelper.getParsedData(html,DELIVERED.MESSAGE));

		Assert.assertTrue(notifyHelper.getParsedData(html,DELIVERED.ORDER_NO).contains(returnTrimmedString(releaseEntry.getDisplayStoreOrderId())));
		log.info("\n Order No. :" +notifyHelper.getParsedData(html,DELIVERED.ORDER_NO));

		Assert.assertNotNull(notifyHelper.getParsedData(html,DELIVERED.PRODUCT_DETAILS));
		log.info("\n Product Details :" +notifyHelper.getParsedData(html,DELIVERED.PRODUCT_DETAILS));

		Assert.assertTrue(notifyHelper.getParsedData(html,DELIVERED.PACKAGE_VALUE).contains(returnTrimmedString(lineEntry.getFinalAmount().toString())));
		log.info("\n Package Value :" +notifyHelper.getParsedData(html,DELIVERED.PACKAGE_VALUE));

		Assert.assertFalse(Jsoup.parse(html).text().contains("$"));

	}
	//Triggers failed delivery email & sms and checks the contents
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression", "ExhaustiveRegression","RMSService" },
			enabled=true,priority=5,description = "Triggers delivered email and checks the contents")
	public void verifyFailedDelivery() throws Exception {

		notif.login_delete_emails(emailId, password);
		notifyHelper.deleteallSMSRecords(mobilenumber);
		String orderID = notifyHelper.createAndMarkOrderToStatus(new String[] {FAILED_DELIVERED.SKU_QUANTITY}, ReleaseStatus.FDTODL, "DL", BLRAddressId);

		OMSServiceHelper omsHelper = new OMSServiceHelper();
		OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
		OrderReleaseEntry releaseEntry = omsServiceHelper.getOrderReleaseEntry(omsServiceHelper.getReleaseId(orderEntry.getId().toString()));
		OrderLineEntry lineEntry = omsHelper.getOrderLineEntry(releaseEntry.getOrderLines().get(0).getId().toString());

		Thread.sleep(10000);

		email.login(emailId, password);
		String html = email.fetchContent("We were unsuccessful in our attempt to deliver your Myntra shipment");

		Assert.assertFalse(Jsoup.parse(html).text().contains("$"));

		//Failed Delivery SMS
		log.info("\n Order Dispatched SMS :" +notifyHelper.getSMS(mobilenumber,FAILED_DELIVERED.FAILEDDELIVERY_SMS_ID));
		Assert.assertNotNull(notifyHelper.getSMS(mobilenumber,FAILED_DELIVERED.FAILEDDELIVERY_SMS_ID));
		Assert.assertTrue(notifyHelper.getSMS(mobilenumber,FAILED_DELIVERED.FAILEDDELIVERY_SMS_ID).contains(returnTrimmedString(releaseEntry.getDisplayStoreOrderId())));
		Assert.assertTrue(notifyHelper.getSMS(mobilenumber,FAILED_DELIVERED.FAILEDDELIVERY_SMS_ID).contains(returnTrimmedString(releaseEntry.getId().toString())));
		Assert.assertTrue(notifyHelper.getSMS(mobilenumber,FAILED_DELIVERED.FAILEDDELIVERY_SMS_ID).toLowerCase().contains(returnTrimmedString(releaseEntry.getReceiverName())));
		Assert.assertFalse(notifyHelper.getSMS(mobilenumber,FAILED_DELIVERED.FAILEDDELIVERY_SMS_ID).contains("$"));

	}
	//Triggers exchange confirmation email and checks the contents
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression", "ExhaustiveRegression","RMSService" },
			enabled=true,priority=6,description = "Triggers exchange confirmation email and checks the contents")
	public void verifyExchangeConfirmed() throws Exception {

		notif.login_delete_emails(emailId, password);
		notifyHelper.deleteallSMSRecords(mobilenumber);
		String orderID = lmsServiceHelper.createAndMarkOrderToStatus(login, new String[] {EXCHANGE_CONFIRMED.SKU_QUANTITY}, ReleaseStatus.DL, "C", BLRAddressId, false, PaymentMode.CC);
		String orderReleaseID = omsServiceHelper.getReleaseId(orderID);
		com.myntra.oms.client.entry.OrderReleaseEntry orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(orderReleaseID);
		Long lineID = orderReleaseEntry.getOrderLines().get(0).getId();
		ExchangeOrderResponse exchangeOrderResponse =(ExchangeOrderResponse) ppsServiceHelper.createExchange(""+orderID, ""+lineID, "DNL", 1, EXCHANGE_CONFIRMED.EXCHANGED_SKU);
		Assert.assertNotNull(exchangeOrderResponse.getSuccess(), "Unable to create exchange");
		String exchangeOrderID = exchangeOrderResponse.getExchangeOrderId();
		log.info(exchangeOrderID);

		List<ReleaseEntry> releaseEntries = new ArrayList<>();
		releaseEntries.add(new ReleaseEntry.Builder(omsServiceHelper.getReleaseId(exchangeOrderID), ReleaseStatus.DL).build());
		ReleaseEntryList releaseEntryList= new ReleaseEntryList(releaseEntries);
		processRelease.processReleaseToStatus(releaseEntryList);

		//processRelease.processReleaseToStatus(new ReleaseEntry.Builder(omsServiceHelper.getReleaseId(exchangeOrderID), ReleaseStatus.DL).build());
		String returnID = lmsHepler.getReturnIdFromOrderId(""+orderReleaseID);
		Map<String, Object> pickup = DBUtilities.exSelectQueryForSingleRecord("select * from pickup where return_id = "+returnID, "lms");
		long destWarehouseId = (long)pickup.get("dest_warehouse_id");
		lmsServiceHelper.transferShipmentBackToWH(returnID, destWarehouseId , 5,"DC","WH");

		Thread.sleep(10000);

		email.login(emailId, password);
		String html = email.fetchContent("Your Myntra exchange request confirmation");
		OMSServiceHelper omsHelper = new OMSServiceHelper();
		OrderEntry orderEntry = omsServiceHelper.getOrderEntry(exchangeOrderID);
		OrderReleaseEntry releaseEntry = omsServiceHelper.getOrderReleaseEntry(omsServiceHelper.getReleaseId(orderEntry.getId().toString()));
		OrderLineEntry lineEntry = omsHelper.getOrderLineEntry(releaseEntry.getOrderLines().get(0).getId().toString());

		Assert.assertTrue(notifyHelper.getParsedData(html,EXCHANGE_CONFIRMED.EXCHANGE_NO).contains(returnTrimmedString(releaseEntry.getOrderId().toString())));
		log.info("\n Exchange Id :" +notifyHelper.getParsedData(html,EXCHANGE_CONFIRMED.EXCHANGE_NO));

		Assert.assertNotNull(notifyHelper.getParsedData(html,EXCHANGE_CONFIRMED.EXCHANGE_DATE));
		log.info("\n Exchange Date :" +notifyHelper.getParsedData(html,EXCHANGE_CONFIRMED.EXCHANGE_DATE));

		Assert.assertEquals(notifyHelper.getParsedData(html,EXCHANGE_CONFIRMED.EXCHANGE_COURIER),EXCHANGE_CONFIRMED.COURIER);
		log.info("\n Exchange Courier :" +notifyHelper.getParsedData(html,EXCHANGE_CONFIRMED.EXCHANGE_COURIER));

		Assert.assertEquals(notifyHelper.getParsedData(html,EXCHANGE_CONFIRMED.ORIGINAL_SIZE),EXCHANGE_CONFIRMED.ORIGINAL_SIZE2);
		log.info("\n Original Size :" +notifyHelper.getParsedData(html,EXCHANGE_CONFIRMED.ORIGINAL_SIZE));

		Assert.assertEquals(notifyHelper.getParsedData(html,EXCHANGE_CONFIRMED.EXCHANGE_SIZE),EXCHANGE_CONFIRMED.EXCHANGE_SIZE2);
		log.info("\n Exchange Size :" +notifyHelper.getParsedData(html,EXCHANGE_CONFIRMED.EXCHANGE_SIZE));

		Assert.assertNotNull(notifyHelper.getParsedData(html,EXCHANGE_CONFIRMED.ADDRESS));
		log.info("\n Address :" +notifyHelper.getParsedData(html,EXCHANGE_CONFIRMED.ADDRESS));

		Assert.assertFalse(Jsoup.parse(html).text().contains("$"));

	}

	//Triggers exchange dispatched email and checks the contents
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression", "ExhaustiveRegression","RMSService" },
			enabled=true,priority=7,description = "Triggers exchange dispatched email and checks the contents")
	public void verifyExchangeDispatched() throws Exception {

		notif.login_delete_emails(emailId, password);
		notifyHelper.deleteallSMSRecords(mobilenumber);
		String orderID = lmsServiceHelper.createAndMarkOrderToStatus(login, new String[] {EXCHANGE_DISPATCHED.SKU_QUANTITY}, ReleaseStatus.DL, "C", BLRAddressId, false, PaymentMode.CC);
		String orderReleaseID = omsServiceHelper.getReleaseId(orderID);
		com.myntra.oms.client.entry.OrderReleaseEntry orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(orderReleaseID);
		Long lineID = orderReleaseEntry.getOrderLines().get(0).getId();
		ExchangeOrderResponse exchangeOrderResponse =(ExchangeOrderResponse) ppsServiceHelper.createExchange(""+orderID, ""+lineID, "DNL", 1, EXCHANGE_DISPATCHED.EXCHANGED_SKU);
		Assert.assertNotNull(exchangeOrderResponse.getSuccess(), "Unable to create exchange");
		String exchangeOrderID = exchangeOrderResponse.getExchangeOrderId();

		List<ReleaseEntry> releaseEntries = new ArrayList<>();
		releaseEntries.add(new ReleaseEntry.Builder(exchangeOrderID, ReleaseStatus.PK).build());
		ReleaseEntryList releaseEntryList= new ReleaseEntryList(releaseEntries);
		processRelease.processReleaseToStatus(releaseEntryList);

		//processRelease.processReleaseToStatus(new ReleaseEntry.Builder(omsServiceHelper.getReleaseId(exchangeOrderID), ReleaseStatus.DL).build());
		String returnID = lmsHepler.getReturnIdFromOrderId(""+orderReleaseID);
		Map<String, Object> pickup = DBUtilities.exSelectQueryForSingleRecord("select * from pickup where return_id = "+returnID, "lms");
		long destWarehouseId = (long)pickup.get("dest_warehouse_id");
		lmsServiceHelper.transferShipmentBackToWH(returnID, destWarehouseId , 5,"DC","WH");

		Thread.sleep(10000);
		email.login(emailId, password);
		String html = email.fetchContent("Your exchange order has been dispatched from the warehouse");
		OMSServiceHelper omsHelper = new OMSServiceHelper();
		OrderEntry orderEntry = omsServiceHelper.getOrderEntry(exchangeOrderID);
		OrderReleaseEntry releaseEntry = omsServiceHelper.getOrderReleaseEntry(omsServiceHelper.getReleaseId(orderEntry.getId().toString()));
		OrderLineEntry lineEntry = omsHelper.getOrderLineEntry(releaseEntry.getOrderLines().get(0).getId().toString());

		Assert.assertTrue(notifyHelper.getParsedData(html,EXCHANGE_DISPATCHED.EXCHANGE_NO).contains(returnTrimmedString(releaseEntry.getOrderId().toString())));
		log.info("\n Exchange Id :" +notifyHelper.getParsedData(html,EXCHANGE_DISPATCHED.EXCHANGE_NO));

		Assert.assertNotNull(notifyHelper.getParsedData(html,EXCHANGE_DISPATCHED.EXCHANGE_DATE));
		log.info("\n Exchange Date :" +notifyHelper.getParsedData(html,EXCHANGE_DISPATCHED.EXCHANGE_DATE));

		Assert.assertEquals(notifyHelper.getParsedData(html,EXCHANGE_DISPATCHED.EXCHANGE_COURIER),EXCHANGE_DISPATCHED.COURIER);
		log.info("\n Exchange Courier :" +notifyHelper.getParsedData(html,EXCHANGE_DISPATCHED.EXCHANGE_COURIER));

		Assert.assertEquals(notifyHelper.getParsedData(html,EXCHANGE_DISPATCHED.PRODUCT_NAME),EXCHANGE_DISPATCHED.STYLE);
		log.info("\n Exchange Product Name :" +notifyHelper.getParsedData(html,EXCHANGE_DISPATCHED.PRODUCT_NAME));

		Assert.assertEquals(notifyHelper.getParsedData(html,EXCHANGE_DISPATCHED.ORIGINAL_SIZE),EXCHANGE_DISPATCHED.ORIGINAL_SIZE2);
		log.info("\n Original Size :" +notifyHelper.getParsedData(html,EXCHANGE_DISPATCHED.ORIGINAL_SIZE));

		Assert.assertEquals(notifyHelper.getParsedData(html,EXCHANGE_DISPATCHED.EXCHANGE_SIZE),EXCHANGE_DISPATCHED.EXCHANGE_SIZE2);
		log.info("\n Exchange Size :" +notifyHelper.getParsedData(html,EXCHANGE_DISPATCHED.EXCHANGE_SIZE));

		Assert.assertFalse(Jsoup.parse(html).text().contains("$"));

	}

	//Triggers exchange out for delivery email and checks the contents
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression", "ExhaustiveRegression","RMSService" },
			enabled=true,priority=8,description = "Triggers exchange out for delivery email and checks the contents")
	public void verifyExchangeOutforDelivery() throws Exception {

		notif.login_delete_emails(emailId, password);
		String orderID = lmsServiceHelper.createAndMarkOrderToStatus(login, new String[] {EXCHANGE_OUTFORDELIVERY.SKU_QUANTITY}, ReleaseStatus.DL, "C", BLRAddressId, false, PaymentMode.CC);
		String orderReleaseID = omsServiceHelper.getReleaseId(orderID);
		com.myntra.oms.client.entry.OrderReleaseEntry orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(orderReleaseID);
		Long lineID = orderReleaseEntry.getOrderLines().get(0).getId();
		ExchangeOrderResponse exchangeOrderResponse =(ExchangeOrderResponse) ppsServiceHelper.createExchange(""+orderID, ""+lineID, "DNL", 1, EXCHANGE_OUTFORDELIVERY.EXCHANGED_SKU);
		Assert.assertNotNull(exchangeOrderResponse.getSuccess(), "Unable to create exchange");
		String exchangeOrderID = exchangeOrderResponse.getExchangeOrderId();

		List<ReleaseEntry> releaseEntries = new ArrayList<>();
		releaseEntries.add(new ReleaseEntry.Builder(omsServiceHelper.getReleaseId(exchangeOrderID), ReleaseStatus.DL).build());
		ReleaseEntryList releaseEntryList= new ReleaseEntryList(releaseEntries);
		processRelease.processReleaseToStatus(releaseEntryList);

		//processRelease.processReleaseToStatus(new ReleaseEntry.Builder(omsServiceHelper.getReleaseId(exchangeOrderID), ReleaseStatus.DL).build());
		String returnID = lmsHepler.getReturnIdFromOrderId(""+orderReleaseID);
		Map<String, Object> pickup = DBUtilities.exSelectQueryForSingleRecord("select * from pickup where return_id = "+returnID, "lms");
		long destWarehouseId = (long)pickup.get("dest_warehouse_id");
		lmsServiceHelper.transferShipmentBackToWH(returnID, destWarehouseId , 5,"DC","WH");

		Thread.sleep(10000);

		email.login(emailId, password);
		String html = email.fetchContent("Your Myntra exchange order is out for delivery");
		OMSServiceHelper omsHelper = new OMSServiceHelper();
		OrderEntry orderEntry = omsServiceHelper.getOrderEntry(exchangeOrderID);
		OrderReleaseEntry releaseEntry = omsServiceHelper.getOrderReleaseEntry(omsServiceHelper.getReleaseId(orderEntry.getId().toString()));
		OrderLineEntry lineEntry = omsHelper.getOrderLineEntry(releaseEntry.getOrderLines().get(0).getId().toString());

		Assert.assertTrue(notifyHelper.getParsedData(html,EXCHANGE_OUTFORDELIVERY.EXCHANGE_NO).contains(returnTrimmedString(releaseEntry.getOrderId().toString())));
		log.info("\n Exchange Id :" +notifyHelper.getParsedData(html,EXCHANGE_OUTFORDELIVERY.EXCHANGE_NO));

		Assert.assertNotNull(notifyHelper.getParsedData(html,EXCHANGE_OUTFORDELIVERY.EXCHANGE_SDA));
		log.info("\n Exchange SDA Name :" +notifyHelper.getParsedData(html,EXCHANGE_OUTFORDELIVERY.EXCHANGE_SDA));

		Assert.assertNotNull(notifyHelper.getParsedData(html,EXCHANGE_OUTFORDELIVERY.EXCHANGE_SDA_MOBILE));
		log.info("\n Exchange SDA Mobile :" +notifyHelper.getParsedData(html,EXCHANGE_OUTFORDELIVERY.EXCHANGE_SDA_MOBILE));

		Assert.assertEquals(notifyHelper.getParsedData(html,EXCHANGE_OUTFORDELIVERY.EXCHANGE_COURIER),EXCHANGE_OUTFORDELIVERY.COURIER);
		log.info("\n Exchange Courier :" +notifyHelper.getParsedData(html,EXCHANGE_OUTFORDELIVERY.EXCHANGE_COURIER));

		Assert.assertEquals(notifyHelper.getParsedData(html,EXCHANGE_OUTFORDELIVERY.PRODUCT_NAME),EXCHANGE_OUTFORDELIVERY.STYLE);
		log.info("\n Exchange Product Name :" +notifyHelper.getParsedData(html,EXCHANGE_OUTFORDELIVERY.PRODUCT_NAME));

		Assert.assertEquals(notifyHelper.getParsedData(html,EXCHANGE_OUTFORDELIVERY.ORIGINAL_SIZE),EXCHANGE_OUTFORDELIVERY.ORIGINAL_SIZE2);
		log.info("\n Original Size :" +notifyHelper.getParsedData(html,EXCHANGE_OUTFORDELIVERY.ORIGINAL_SIZE));

		Assert.assertEquals(notifyHelper.getParsedData(html,EXCHANGE_OUTFORDELIVERY.EXCHANGE_SIZE),EXCHANGE_OUTFORDELIVERY.EXCHANGE_SIZE2);
		log.info("\n Exchange Size :" +notifyHelper.getParsedData(html,EXCHANGE_OUTFORDELIVERY.EXCHANGE_SIZE));

		Assert.assertNotNull(notifyHelper.getParsedData(html,EXCHANGE_OUTFORDELIVERY.ADDRESS));
		log.info("\n Address :" +notifyHelper.getParsedData(html,EXCHANGE_OUTFORDELIVERY.ADDRESS));

		Assert.assertFalse(Jsoup.parse(html).text().contains("$"));

	}

	//Triggers exchange delivered email and checks the contents
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression", "ExhaustiveRegression","RMSService" },
			enabled=true,priority=9,description = "Triggers exchange delivered email and checks the contents")
	public void verifyExchangeDelivered() throws Exception {

		notif.login_delete_emails(emailId, password);
		notifyHelper.deleteallSMSRecords(mobilenumber);
		//long orderID = lmsServiceHelper.createAndMarkOrderToStatus(login, new String[] {EXCHANGE_DELIVERED.SKU_QUANTITY}, "DL", "C", BLRAddressId, false, PaymentMode.CC);
		String orderID = notifyHelper.createAndMarkOrderToStatus(emailId,password, new String[] {EXCHANGE_DELIVERED.SKU_QUANTITY}, ReleaseStatus.DL, "C", BLRAddressId, false, PaymentMode.CC);

		String orderReleaseID = omsServiceHelper.getReleaseId(orderID);
		com.myntra.oms.client.entry.OrderReleaseEntry orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(orderReleaseID);
		Long lineID = orderReleaseEntry.getOrderLines().get(0).getId();
		ExchangeOrderResponse exchangeOrderResponse =(ExchangeOrderResponse) ppsServiceHelper.createExchange(""+orderID, ""+lineID, "DNL", 1, EXCHANGE_DELIVERED.EXCHANGED_SKU);
		Assert.assertNotNull(exchangeOrderResponse.getSuccess(), "Unable to create exchange");
		String exchangeOrderID = exchangeOrderResponse.getExchangeOrderId();


		List<ReleaseEntry> releaseEntries = new ArrayList<>();
		releaseEntries.add(new ReleaseEntry.Builder(omsServiceHelper.getReleaseId(exchangeOrderID), ReleaseStatus.DL).build());
		ReleaseEntryList releaseEntryList= new ReleaseEntryList(releaseEntries);
		processRelease.processReleaseToStatus(releaseEntryList);

		//processRelease.processReleaseToStatus(new ReleaseEntry.Builder(omsServiceHelper.getReleaseId(exchangeOrderID), ReleaseStatus.DL).build());
		String returnID = lmsHepler.getReturnIdFromOrderId(""+orderReleaseID);
		Map<String, Object> pickup = DBUtilities.exSelectQueryForSingleRecord("select * from pickup where return_id = "+returnID, "lms");
		long destWarehouseId = (long)pickup.get("dest_warehouse_id");
		lmsServiceHelper.transferShipmentBackToWH(returnID, destWarehouseId , 5,"DC","WH");

		Thread.sleep(10000);

		email.login(emailId, password);
		String html = email.fetchContent("Your Myntra exchange order has been delivered");
		OMSServiceHelper omsHelper = new OMSServiceHelper();
		OrderEntry orderEntry = omsServiceHelper.getOrderEntry(exchangeOrderID);
		OrderReleaseEntry releaseEntry = omsServiceHelper.getOrderReleaseEntry(omsServiceHelper.getReleaseId(orderEntry.getId().toString()));
		OrderLineEntry lineEntry = omsHelper.getOrderLineEntry(releaseEntry.getOrderLines().get(0).getId().toString());

		Assert.assertTrue(notifyHelper.getParsedData(html,EXCHANGE_DELIVERED.EXCHANGE_NO).contains(returnTrimmedString(releaseEntry.getOrderId().toString())));
		log.info("\n Exchange Id :" +notifyHelper.getParsedData(html,EXCHANGE_DELIVERED.EXCHANGE_NO));

		Assert.assertNotNull(notifyHelper.getParsedData(html,EXCHANGE_DELIVERED.EXCHANGE_NPS));
		log.info("\n Exchange NPS Present :" +notifyHelper.getParsedData(html,EXCHANGE_DELIVERED.EXCHANGE_NPS));

		Assert.assertEquals(notifyHelper.getParsedData(html,EXCHANGE_DELIVERED.PRODUCT_NAME),EXCHANGE_DELIVERED.STYLE);
		log.info("\n Exchange Product Name :" +notifyHelper.getParsedData(html,EXCHANGE_DELIVERED.PRODUCT_NAME));

		Assert.assertEquals(notifyHelper.getParsedData(html,EXCHANGE_DELIVERED.ORIGINAL_SIZE),EXCHANGE_DELIVERED.ORIGINAL_SIZE2);
		log.info("\n Original Size :" +notifyHelper.getParsedData(html,EXCHANGE_DELIVERED.ORIGINAL_SIZE));

		Assert.assertEquals(notifyHelper.getParsedData(html,EXCHANGE_DELIVERED.EXCHANGE_SIZE),EXCHANGE_DELIVERED.EXCHANGE_SIZE2);
		log.info("\n Exchange Size :" +notifyHelper.getParsedData(html,EXCHANGE_DELIVERED.EXCHANGE_SIZE));

		Assert.assertFalse(Jsoup.parse(html).text().contains("$"));

	}
	//Triggers order cancellation email & SMS and checks the contents
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression", "ExhaustiveRegression","RMSService" },
			enabled=true,priority=10, description = "Triggers order cancellation email and checks the contents")

	public void verifyOrderCancelled() throws Exception {
		notifyHelper.deleteallSMSRecords(mobilenumber);
		notif.login_delete_emails(emailId, password);
		String orderID = e2e.createOrder(emailId, password, BLRAddressId, new String[] {ORDER_CONFIRM.SKU_QUANTITY}, "", false, false, false, "", false);
		OMSServiceHelper omsHelper = new OMSServiceHelper();
		OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
		OrderReleaseEntry releaseEntry = omsServiceHelper.getOrderReleaseEntry(omsServiceHelper.getReleaseId(orderEntry.getId().toString()));
		OrderLineEntry lineEntry = omsHelper.getOrderLineEntry(releaseEntry.getOrderLines().get(0).getId().toString());
		Thread.sleep(30000);
		//omsServiceHelper.cancelOrderRelease(releaseEntry.getId(), "1", emailId, "Test");
		omsServiceHelper.cancelOrder(orderID, "1", emailId, "Test");


		Thread.sleep(20000);
		email.login(emailId, password);
		String html = email.fetchContent("Your Myntra order has been cancelled");

		Assert.assertTrue(Jsoup.parse(html).text().contains(releaseEntry.getDisplayStoreOrderId().toString()));
		Assert.assertFalse(Jsoup.parse(html).text().contains("$"));


	}


	//Triggers return confirmation email & SMS and checks the contents
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression", "ExhaustiveRegression","RMSService" },
			enabled=true,priority=10, description = "Triggers return confirmation email and checks the contents")

	public void verifyReturnConfirmed() throws Exception {
		notifyHelper.deleteallSMSRecords(mobilenumber);
		notif.login_delete_emails(emailId, password);
					/*Long orderID = e2e.createOrder(emailId, password, BLRAddressId, new String[] {ORDER_CONFIRM.SKU_QUANTITY}, "", false, false, false, "", false);
					OMSServiceHelper omsHelper = new OMSServiceHelper();
					OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
					OrderReleaseEntry releaseEntry = omsServiceHelper.getOrderReleaseEntry(omsServiceHelper.getRelaeseId(orderEntry.getId()));
					OrderLineEntry lineEntry = omsHelper.getOrderLineEntry(releaseEntry.getOrderLines().get(0).getId().toString()); */

		RmsDP dp = new RmsDP();
		dp.CreateReturnRRQP_delete();
		dp.CreateReturnRRQP_data();

		String updateEmails="UPDATE order_release SET login = 'end2endautomation4@gmail.com'  WHERE order_id_fk=70041385;";
		DBUtilities.exUpdateQuery(updateEmails, "myntra_oms");

		String updateEmails2="UPDATE order_release SET email = 'end2endautomation4@gmail.com'  WHERE order_id_fk=70041385;";
		DBUtilities.exUpdateQuery(updateEmails2, "myntra_oms");

		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn("52697199", 1, ReturnMode.PICKUP, 27L);
		ReturnEntry returnEntry = returnResponse.getData().get(0);

		String returnId = returnEntry.getId().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);

		returnHelper.returnStatusProcessNew(returnEntry.getId().toString(), ReturnStatus.RRQP, ReturnStatus.RPI);
		returnHelper.returnPickupProcessNew(returnEntry.getId().toString(), ReturnStatus.RPI);

		ReturnLineEntry entry = returnEntry2.getReturnLineEntries().get(0);
		returnHelper.returnLineStatusProcessNew(entry.getId().toString(), ReturnLineStatus.RPU, ReturnLineStatus.RRC, "70041385", returnEntry.getId().toString(), "100026329239", "ML12408712306", "Pawell", 36,"","","");
		ReturnLineResponse returnResponse3 = returnHelper.returnLineStatusProcessNew(entry.getId().toString(), ReturnLineStatus.RRC, ReturnLineStatus.RQP, "70041385", returnEntry.getId().toString(), "100026329239", "", "Pawell", 1,"Q1","","");
		ReturnLineEntry returnEntry3 = returnResponse3.getData().get(0);

		Assert.assertEquals("70041385", ""+returnEntry3.getOrderId());
		Assert.assertEquals(ReturnLineStatus.RQP, returnEntry3.getStatusCode());

		Thread.sleep(5000);
		ReturnResponse returnResponse4 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry4 = returnResponse4.getData().get(0);

		//code for Return email verification

		Thread.sleep(20000);
		email.login(emailId, password);
		String html = email.fetchContent("Your Myntra return request accepted");

		Assert.assertTrue(notifyHelper.getParsedData(html,RETURN_CONFIRMED.RETURN_NUMBER).contains(returnTrimmedString(returnEntry4.getId().toString())));
		log.info("\n Return Number :" +notifyHelper.getParsedData(html,RETURN_CONFIRMED.RETURN_NUMBER));

		Assert.assertTrue(notifyHelper.getParsedData(html,RETURN_CONFIRMED.RETURN_MODE).contains(returnTrimmedString(returnEntry4.getReturnMode().toString())));
		log.info("\n Return Mode :" +notifyHelper.getParsedData(html,RETURN_CONFIRMED.RETURN_MODE));

		Assert.assertTrue(notifyHelper.getParsedData(html,RETURN_CONFIRMED.RETURN_AMOUNT).contains(returnTrimmedString(returnEntry4.getReturnRefundDetailsEntry().getRefundAmount().toString())));
		log.info("\n Return Amount :" +notifyHelper.getParsedData(html,RETURN_CONFIRMED.RETURN_AMOUNT));

		Assert.assertTrue(notifyHelper.getParsedData(html,RETURN_CONFIRMED.COURIER).contains(returnTrimmedString(RETURN_CONFIRMED.COURIER2)));
		log.info("\n Courier :" +notifyHelper.getParsedData(html,RETURN_CONFIRMED.COURIER));

		Assert.assertTrue(notifyHelper.getParsedData(html,RETURN_CONFIRMED.ADDRESS).contains(returnTrimmedString(returnEntry4.getMobile())));
		log.info("\n Address :" +notifyHelper.getParsedData(html,RETURN_CONFIRMED.ADDRESS));

		Assert.assertNotNull(notifyHelper.getParsedData(html,RETURN_CONFIRMED.ADDRESS));

		//Return Confirmation SMS
		log.info("\n Return COnfirmation SMS :" +notifyHelper.getSMS(mobilenumber,RETURN_CONFIRMED.RETURNCONFIRMED_SMS_ID));
		Assert.assertNotNull(notifyHelper.getSMS(mobilenumber,RETURN_CONFIRMED.RETURNCONFIRMED_SMS_ID));
		Assert.assertTrue(notifyHelper.getSMS(mobilenumber,RETURN_CONFIRMED.RETURNCONFIRMED_SMS_ID).contains(returnTrimmedString(returnEntry4.getId().toString())));


	}

	//Triggers return processed email & SMS and checks the contents
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression", "ExhaustiveRegression","RMSService" },
			enabled=true,priority=11, description = "Triggers return processed email and checks the contents")

	public void verifyReturnProcessed() throws Exception {
		notifyHelper.deleteallSMSRecords(mobilenumber);
		notif.login_delete_emails(emailId, password);
					/*Long orderID = e2e.createOrder(emailId, password, BLRAddressId, new String[] {ORDER_CONFIRM.SKU_QUANTITY}, "", false, false, false, "", false);
					OMSServiceHelper omsHelper = new OMSServiceHelper();
					OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
					OrderReleaseEntry releaseEntry = omsServiceHelper.getOrderReleaseEntry(omsServiceHelper.getRelaeseId(orderEntry.getId()));
					OrderLineEntry lineEntry = omsHelper.getOrderLineEntry(releaseEntry.getOrderLines().get(0).getId().toString()); */

		RmsDP dp = new RmsDP();
		dp.CreateReturnRRQP_delete();
		dp.CreateReturnRRQP_data();

		String updateEmails="UPDATE order_release SET login = 'end2endautomation4@gmail.com'  WHERE order_id_fk=70041385;";
		DBUtilities.exUpdateQuery(updateEmails, "myntra_oms");

		String updateEmails2="UPDATE order_release SET email = 'end2endautomation4@gmail.com'  WHERE order_id_fk=70041385;";
		DBUtilities.exUpdateQuery(updateEmails2, "myntra_oms");

		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn("52697199", 1, ReturnMode.PICKUP, 27L);
		ReturnEntry returnEntry = returnResponse.getData().get(0);

		String returnId = returnEntry.getId().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);

		returnHelper.returnStatusProcessNew(returnEntry.getId().toString(), ReturnStatus.RRQP, ReturnStatus.RPI);
		returnHelper.returnPickupProcessNew(returnEntry.getId().toString(), ReturnStatus.RPI);

		ReturnLineEntry entry = returnEntry2.getReturnLineEntries().get(0);
		returnHelper.returnLineStatusProcessNew(entry.getId().toString(), ReturnLineStatus.RPU, ReturnLineStatus.RRC, "70041385", returnEntry.getId().toString(), "100026329239", "ML12408712306", "Pawell", 36,"","","");
		ReturnLineResponse returnResponse3 = returnHelper.returnLineStatusProcessNew(entry.getId().toString(), ReturnLineStatus.RRC, ReturnLineStatus.RQP, "70041385", returnEntry.getId().toString(), "100026329239", "", "Pawell", 1,"Q1","","");
		ReturnLineEntry returnEntry3 = returnResponse3.getData().get(0);

		Assert.assertEquals("70041385", ""+returnEntry3.getOrderId());
		Assert.assertEquals(ReturnLineStatus.RQP, returnEntry3.getStatusCode());

		Thread.sleep(5000);
		ReturnResponse returnResponse4 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry4 = returnResponse4.getData().get(0);

		//code for Return email verification

		Thread.sleep(20000);
		email.login(emailId, password);
		String html = email.fetchContent("Your Myntra return request processed. Please share your feedback");

		Assert.assertTrue(notifyHelper.getParsedData(html,RETURN_PROCESSED.RETURN_NUMBER).contains(returnTrimmedString(returnEntry4.getId().toString())));
		log.info("\n Return Number :" +notifyHelper.getParsedData(html,RETURN_PROCESSED.RETURN_NUMBER));

		Assert.assertTrue(notifyHelper.getParsedData(html,RETURN_PROCESSED.RETURN_AMOUNT).contains(returnTrimmedString(returnEntry4.getReturnRefundDetailsEntry().getRefundAmount().toString())));
		log.info("\n Return Amount :" +notifyHelper.getParsedData(html,RETURN_PROCESSED.RETURN_AMOUNT));

		Assert.assertNotNull(notifyHelper.getParsedData(html,RETURN_PROCESSED.NPS));

		Assert.assertEquals(notifyHelper.getParsedData(html,RETURN_PROCESSED.PRODUCT_NAME),RETURN_PROCESSED.STYLE);
		log.info("\n Product Name :" +notifyHelper.getParsedData(html,RETURN_PROCESSED.PRODUCT_NAME));

		Assert.assertTrue(notifyHelper.getParsedData(html,RETURN_PROCESSED.PRODUCT_DETAILS).contains(returnTrimmedString("Size: M")));
		Assert.assertTrue(notifyHelper.getParsedData(html,RETURN_PROCESSED.PRODUCT_DETAILS).contains(returnTrimmedString(returnEntry4.getReturnRefundDetailsEntry().getRefundAmount().toString())));
		log.info("\n Product Detail :" +notifyHelper.getParsedData(html,RETURN_PROCESSED.PRODUCT_DETAILS));

		Assert.assertTrue(notifyHelper.getParsedData(html,RETURN_PROCESSED.AMOUNT_REFUNDED).contains(returnTrimmedString(returnEntry4.getReturnRefundDetailsEntry().getRefundAmount().toString())));
		log.info("\n Return Amount :" +notifyHelper.getParsedData(html,RETURN_PROCESSED.AMOUNT_REFUNDED));

		//Return Processed SMS
		log.info("\n Return Confirmation SMS :" +notifyHelper.getSMS(mobilenumber,RETURN_PROCESSED.RETURNCONFIRMED_SMS_ID));
		Assert.assertNotNull(notifyHelper.getSMS(mobilenumber,RETURN_PROCESSED.RETURNCONFIRMED_SMS_ID));
		Assert.assertTrue(notifyHelper.getSMS(mobilenumber,RETURN_PROCESSED.RETURNCONFIRMED_SMS_ID).contains(returnTrimmedString(returnEntry4.getId().toString())));
		Assert.assertTrue(notifyHelper.getSMS(mobilenumber,RETURN_PROCESSED.RETURNCONFIRMED_SMS_ID).contains(returnTrimmedString(returnEntry4.getReturnRefundDetailsEntry().getRefundAmount().toString())));

	}

	//Triggers return pickup failed email & SMS and checks the contents
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression", "ExhaustiveRegression","RMSService" },
			enabled=true,priority=12, description = "Triggers return pickup failed email and checks the contents")

	public void verifyReturnPickupFailed() throws Exception {
		notifyHelper.deleteallSMSRecords(mobilenumber);
		notif.login_delete_emails(emailId, password);
					/*Long orderID = e2e.createOrder(emailId, password, BLRAddressId, new String[] {ORDER_CONFIRM.SKU_QUANTITY}, "", false, false, false, "", false);
					OMSServiceHelper omsHelper = new OMSServiceHelper();
					OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
					OrderReleaseEntry releaseEntry = omsServiceHelper.getOrderReleaseEntry(omsServiceHelper.getRelaeseId(orderEntry.getId()));
					OrderLineEntry lineEntry = omsHelper.getOrderLineEntry(releaseEntry.getOrderLines().get(0).getId().toString()); */

		RmsDP dp = new RmsDP();
		dp.CreateReturnRRQP_delete();
		dp.CreateReturnRRQP_data();

		String updateEmails="UPDATE order_release SET login = 'end2endautomation4@gmail.com'  WHERE order_id_fk=70041385;";
		DBUtilities.exUpdateQuery(updateEmails, "myntra_oms");

		String updateEmails2="UPDATE order_release SET email = 'end2endautomation4@gmail.com'  WHERE order_id_fk=70041385;";
		DBUtilities.exUpdateQuery(updateEmails2, "myntra_oms");

		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn("52697199", 1, ReturnMode.PICKUP, 27L);
		ReturnEntry returnEntry = returnResponse.getData().get(0);

		String returnId = returnEntry.getId().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);

		returnHelper.returnStatusProcessNew(returnEntry.getId().toString(), ReturnStatus.RRQP, ReturnStatus.RPI);

		returnHelper.returnStatusProcessNew(returnEntry.getId().toString(), ReturnStatus.RPI, ReturnStatus.RPF);
		ReturnResponse returnResponse4 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry4 = returnResponse4.getData().get(0);
		Assert.assertEquals(ReturnStatus.RPF, returnEntry4.getStatusCode());

		Thread.sleep(5000);
		ReturnResponse returnResponse5 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry5 = returnResponse5.getData().get(0);

		//code for Return email verification

		Thread.sleep(20000);
		email.login(emailId, password);
		String html = email.fetchContent("Your Myntra return pickup failed");

		Assert.assertTrue(notifyHelper.getParsedData(html,RETURN_PICKUPFAILED.RETURN_NUMBER).contains(returnTrimmedString(returnEntry5.getId().toString())));
		log.info("\n Return Number :" +notifyHelper.getParsedData(html,RETURN_PICKUPFAILED.RETURN_NUMBER));

		Assert.assertTrue(notifyHelper.getParsedData(html,RETURN_PICKUPFAILED.RETURN_AMOUNT).contains(returnTrimmedString(returnEntry5.getReturnRefundDetailsEntry().getRefundAmount().toString())));
		log.info("\n Return Amount :" +notifyHelper.getParsedData(html,RETURN_PICKUPFAILED.RETURN_AMOUNT));

		Assert.assertNotNull(notifyHelper.getParsedData(html,RETURN_PICKUPFAILED.COURIER));
		log.info("\n Courier :" +notifyHelper.getParsedData(html,RETURN_PICKUPFAILED.COURIER));

		Assert.assertEquals(notifyHelper.getParsedData(html,RETURN_PICKUPFAILED.PRODUCT_NAME),RETURN_PICKUPFAILED.STYLE);
		log.info("\n Product Name :" +notifyHelper.getParsedData(html,RETURN_PICKUPFAILED.PRODUCT_NAME));

		Assert.assertTrue(notifyHelper.getParsedData(html,RETURN_PICKUPFAILED.PRODUCT_DETAILS).contains(returnTrimmedString("Size: M")));
		Assert.assertTrue(notifyHelper.getParsedData(html,RETURN_PICKUPFAILED.PRODUCT_DETAILS).contains(returnTrimmedString(returnEntry5.getReturnRefundDetailsEntry().getRefundAmount().toString())));
		log.info("\n Product Detail :" +notifyHelper.getParsedData(html,RETURN_PICKUPFAILED.PRODUCT_DETAILS));

		Assert.assertTrue(notifyHelper.getParsedData(html,RETURN_PICKUPFAILED.ADDRESS).contains(returnTrimmedString(returnEntry5.getMobile().toString())));
		log.info("\n Address :" +notifyHelper.getParsedData(html,RETURN_PICKUPFAILED.ADDRESS));

		//Return Pickup Failed SMS
		log.info("\n Return Pickup Failed SMS :" +notifyHelper.getSMS(mobilenumber,RETURN_PICKUPFAILED.RETURNCONFIRMED_SMS_ID));
		Assert.assertNotNull(notifyHelper.getSMS(mobilenumber,RETURN_PICKUPFAILED.RETURNCONFIRMED_SMS_ID));
		Assert.assertTrue(notifyHelper.getSMS(mobilenumber,RETURN_PICKUPFAILED.RETURNCONFIRMED_SMS_ID).contains(returnTrimmedString(returnEntry5.getId().toString())));


	}

	//Triggers return re-shipped  email & SMS and checks the contents
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression", "ExhaustiveRegression","RMSService" },
			enabled=true,priority=13, description = "Triggers return re-shipped email and checks the contents")

	public void verifyReturnReshipped() throws Exception {
		notifyHelper.deleteallSMSRecords(mobilenumber);
		notif.login_delete_emails(emailId, password);
					/*Long orderID = e2e.createOrder(emailId, password, BLRAddressId, new String[] {ORDER_CONFIRM.SKU_QUANTITY}, "", false, false, false, "", false);
					OMSServiceHelper omsHelper = new OMSServiceHelper();
					OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
					OrderReleaseEntry releaseEntry = omsServiceHelper.getOrderReleaseEntry(omsServiceHelper.getRelaeseId(orderEntry.getId()));
					OrderLineEntry lineEntry = omsHelper.getOrderLineEntry(releaseEntry.getOrderLines().get(0).getId().toString()); */

		RmsDP dp = new RmsDP();
		dp.CreateReturnRRQP_delete();
		dp.CreateReturnRRQP_data();

		String updateEmails="UPDATE order_release SET login = 'end2endautomation4@gmail.com'  WHERE order_id_fk=70041385;";
		DBUtilities.exUpdateQuery(updateEmails, "myntra_oms");

		String updateEmails2="UPDATE order_release SET email = 'end2endautomation4@gmail.com'  WHERE order_id_fk=70041385;";
		DBUtilities.exUpdateQuery(updateEmails2, "myntra_oms");

		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn("52697199", 1, ReturnMode.SELF_SHIP, 27L);
		ReturnEntry returnEntry = returnResponse.getData().get(0);

		String returnId = returnEntry.getId().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);

		ReturnLineEntry entry = returnEntry2.getReturnLineEntries().get(0);
		returnHelper.returnLineStatusProcessNew(entry.getId().toString(), ReturnLineStatus.RRQS, ReturnLineStatus.RRC, "70041385", returnEntry.getId().toString(), "100026329239", "ML12408712306", "Pawell", 36,"","","");
		returnHelper.ItemBarcodeStamp(returnEntry.getId().toString(), "100026329239");

		ReturnLineResponse returnResponse3 = returnHelper.returnLineStatusProcessNew(entry.getId().toString(), ReturnLineStatus.RRC, ReturnLineStatus.RQF, "70041385", returnEntry.getId().toString(), "", "", "Pawell", 1,"Q2","MD","Damage");
		ReturnLineEntry returnEntry3 = returnResponse3.getData().get(0);

		Assert.assertEquals("70041385", ""+returnEntry3.getOrderId());
		Assert.assertEquals(ReturnLineStatus.RQF, returnEntry3.getStatusCode());
		Assert.assertEquals("Q2", returnEntry3.getQuality());
		Assert.assertEquals("MD", returnEntry3.getReturnLineAdditionalDetailsEntry().getQaFailReason());
		Assert.assertEquals("Damage", returnEntry3.getReturnLineAdditionalDetailsEntry().getQcDescription());

		ReturnLineResponse returnResponse4 = returnHelper.returnLineStatusProcessNew(entry.getId().toString(), ReturnLineStatus.RQF, ReturnLineStatus.RQSF, "70041385", returnEntry.getId().toString(), "", "", "Pawell", 1,"Q2","MD","Damage");
		ReturnLineEntry returnEntry4 = returnResponse4.getData().get(0);

		Assert.assertEquals("70041385", ""+returnEntry4.getOrderId());
		Assert.assertEquals(ReturnLineStatus.RQSF, returnEntry4.getStatusCode());
		Assert.assertEquals("Q2", returnEntry4.getQuality());
		Assert.assertEquals("MD", returnEntry4.getReturnLineAdditionalDetailsEntry().getQaFailReason());
		Assert.assertEquals("Damage", returnEntry4.getReturnLineAdditionalDetailsEntry().getQcDescription());

		ReturnLineResponse returnResponse5 = returnHelper.returnLineStatusProcessNew(entry.getId().toString(), ReturnLineStatus.RQSF, ReturnLineStatus.RQCF, "70041385", returnEntry.getId().toString(), "", "", "Pawell", 1,"Q2","MD","Damage");
		ReturnLineEntry returnEntry5 = returnResponse5.getData().get(0);

		Assert.assertEquals("70041385", ""+returnEntry5.getOrderId());
		Assert.assertEquals(ReturnLineStatus.RQCF, returnEntry5.getStatusCode());
		Assert.assertEquals("Q2", returnEntry5.getQuality());
		Assert.assertEquals("MD", returnEntry5.getReturnLineAdditionalDetailsEntry().getQaFailReason());
		Assert.assertEquals("Damage", returnEntry5.getReturnLineAdditionalDetailsEntry().getQcDescription());

		ReturnResponse returnResponse6 = returnHelper.returnStatusProcessNew(returnEntry.getId().toString(), ReturnStatus.RPC, ReturnStatus.RRRS);
		ReturnEntry returnEntry6 = returnResponse6.getData().get(0);
		Assert.assertEquals(ReturnStatus.RRRS, returnEntry6.getStatusCode());

		ReturnResponse returnResponse7 = returnHelper.returnStatusProcessNew(returnEntry.getId().toString(), ReturnStatus.RRRS, ReturnStatus.RRS);
		ReturnEntry returnEntry7 = returnResponse7.getData().get(0);
		Assert.assertEquals(ReturnStatus.RRS, returnEntry7.getStatusCode());

		ReturnResponse returnResponse8 = returnHelper.returnStatusProcessNew(returnEntry.getId().toString(), ReturnStatus.RRS, ReturnStatus.RSD);
		ReturnEntry returnEntry8 = returnResponse8.getData().get(0);
		Assert.assertEquals(ReturnStatus.RSD, returnEntry8.getStatusCode());

		//code for Return email verification
					
					/*Thread.sleep(20000);
					email.login(emailId, password);
					String html = email.fetchContent("Your Myntra return pickup failed");
					
					Assert.assertTrue(notifyHelper.getParsedData(html,RETURN_PICKUPFAILED.RETURN_NUMBER).contains(returnTrimmedString(returnEntry5.getId().toString())));
					log.info("\n Return Number :" +notifyHelper.getParsedData(html,RETURN_PICKUPFAILED.RETURN_NUMBER));
					
					Assert.assertTrue(notifyHelper.getParsedData(html,RETURN_PICKUPFAILED.RETURN_AMOUNT).contains(returnTrimmedString(returnEntry5.getReturnRefundDetailsEntry().getRefundAmount().toString())));
					log.info("\n Return Amount :" +notifyHelper.getParsedData(html,RETURN_PICKUPFAILED.RETURN_AMOUNT));
					
					Assert.assertNotNull(notifyHelper.getParsedData(html,RETURN_PICKUPFAILED.COURIER));
					log.info("\n Courier :" +notifyHelper.getParsedData(html,RETURN_PICKUPFAILED.COURIER));
					
					Assert.assertEquals(notifyHelper.getParsedData(html,RETURN_PICKUPFAILED.PRODUCT_NAME),"puma men grey solid / plain round neck t-shirt");
					log.info("\n Product Name :" +notifyHelper.getParsedData(html,RETURN_PICKUPFAILED.PRODUCT_NAME));
					
					Assert.assertTrue(notifyHelper.getParsedData(html,RETURN_PICKUPFAILED.PRODUCT_DETAILS).contains(returnTrimmedString("Size: M")));
					Assert.assertTrue(notifyHelper.getParsedData(html,RETURN_PICKUPFAILED.PRODUCT_DETAILS).contains(returnTrimmedString(returnEntry5.getReturnRefundDetailsEntry().getRefundAmount().toString())));
					log.info("\n Product Detail :" +notifyHelper.getParsedData(html,RETURN_PICKUPFAILED.PRODUCT_DETAILS));
					
					Assert.assertTrue(notifyHelper.getParsedData(html,RETURN_PICKUPFAILED.ADDRESS).contains(returnTrimmedString(returnEntry5.getMobile().toString())));
					log.info("\n Address :" +notifyHelper.getParsedData(html,RETURN_PICKUPFAILED.ADDRESS));
					
					//Return Pickup Failed SMS
		            log.info("\n Return Pickup Failed SMS :" +notifyHelper.getSMS(mobilenumber,RETURN_PICKUPFAILED.RETURNCONFIRMED_SMS_ID));
		            Assert.assertNotNull(notifyHelper.getSMS(mobilenumber,RETURN_PICKUPFAILED.RETURNCONFIRMED_SMS_ID));
		            Assert.assertTrue(notifyHelper.getSMS(mobilenumber,RETURN_PICKUPFAILED.RETURNCONFIRMED_SMS_ID).contains(returnTrimmedString(returnEntry5.getId().toString())));
		            */

	}

	//Triggers return declined email & SMS and checks the contents
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression", "ExhaustiveRegression","RMSService" },
			enabled=true,priority=14, description = "Triggers return declined email and checks the contents")

	public void verifyReturnDeclined() throws Exception {
		notifyHelper.deleteallSMSRecords(mobilenumber);
		notif.login_delete_emails(emailId, password);
					/*Long orderID = e2e.createOrder(emailId, password, BLRAddressId, new String[] {ORDER_CONFIRM.SKU_QUANTITY}, "", false, false, false, "", false);
					OMSServiceHelper omsHelper = new OMSServiceHelper();
					OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
					OrderReleaseEntry releaseEntry = omsServiceHelper.getOrderReleaseEntry(omsServiceHelper.getRelaeseId(orderEntry.getId()));
					OrderLineEntry lineEntry = omsHelper.getOrderLineEntry(releaseEntry.getOrderLines().get(0).getId().toString()); */

		RmsDP dp = new RmsDP();
		dp.CreateReturnRRQP_delete();
		dp.CreateReturnRRQP_data();

		String updateEmails="UPDATE order_release SET login = 'end2endautomation4@gmail.com'  WHERE order_id_fk=70041385;";
		DBUtilities.exUpdateQuery(updateEmails, "myntra_oms");

		String updateEmails2="UPDATE order_release SET email = 'end2endautomation4@gmail.com'  WHERE order_id_fk=70041385;";
		DBUtilities.exUpdateQuery(updateEmails2, "myntra_oms");

		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn("52697199", 1, ReturnMode.PICKUP, 27L);
		ReturnEntry returnEntry = returnResponse.getData().get(0);

		String returnId = returnEntry.getId().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);

		returnHelper.returnStatusProcessNew(returnEntry.getId().toString(), ReturnStatus.RRQP, ReturnStatus.RPI);

		returnHelper.returnStatusProcessNew(returnEntry.getId().toString(), ReturnStatus.RPI, ReturnStatus.RRD);
		ReturnResponse returnResponse4 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry4 = returnResponse4.getData().get(0);
		Assert.assertEquals(ReturnStatus.RRD, returnEntry4.getStatusCode());

		Thread.sleep(5000);
		ReturnResponse returnResponse5 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry5 = returnResponse5.getData().get(0);

		//code for Return email verification

		Thread.sleep(20000);
		email.login(emailId, password);
		String html = email.fetchContent("Your Myntra return request cancelled");

		Assert.assertTrue(notifyHelper.getParsedData(html,RETURN_DECLINED.RETURN_NUMBER).contains(returnTrimmedString(returnEntry5.getId().toString())));
		log.info("\n Return Number :" +notifyHelper.getParsedData(html,RETURN_DECLINED.RETURN_NUMBER));

		Assert.assertEquals(notifyHelper.getParsedData(html,RETURN_DECLINED.PRODUCT_NAME),RETURN_DECLINED.STYLE);
		log.info("\n Product Name :" +notifyHelper.getParsedData(html,RETURN_DECLINED.PRODUCT_NAME));

		Assert.assertTrue(notifyHelper.getParsedData(html,RETURN_DECLINED.PRODUCT_DETAILS).contains(returnTrimmedString("Size: M")));
		Assert.assertTrue(notifyHelper.getParsedData(html,RETURN_DECLINED.PRODUCT_DETAILS).contains(returnTrimmedString(returnEntry5.getReturnRefundDetailsEntry().getRefundAmount().toString())));
		log.info("\n Product Detail :" +notifyHelper.getParsedData(html,RETURN_DECLINED.PRODUCT_DETAILS));


		//Return declined SMS
		log.info("\n Return Declined :" +notifyHelper.getSMS(mobilenumber,RETURN_DECLINED.RETURNCONFIRMED_SMS_ID));
		Assert.assertNotNull(notifyHelper.getSMS(mobilenumber,RETURN_DECLINED.RETURNCONFIRMED_SMS_ID));
		Assert.assertTrue(notifyHelper.getSMS(mobilenumber,RETURN_DECLINED.RETURNCONFIRMED_SMS_ID).contains(returnTrimmedString(returnEntry5.getId().toString())));


	}

	//Triggers return on-hold rejected email & SMS and checks the contents
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression", "ExhaustiveRegression","RMSService" },
			enabled=true,priority=15, description = "Triggers return on-hold rejected email and checks the contents")

	public void verifyReturnOnholdRejected() throws Exception {
		notifyHelper.deleteallSMSRecords(mobilenumber);
		notif.login_delete_emails(emailId, password);
					/*Long orderID = e2e.createOrder(emailId, password, BLRAddressId, new String[] {ORDER_CONFIRM.SKU_QUANTITY}, "", false, false, false, "", false);
					OMSServiceHelper omsHelper = new OMSServiceHelper();
					OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
					OrderReleaseEntry releaseEntry = omsServiceHelper.getOrderReleaseEntry(omsServiceHelper.getRelaeseId(orderEntry.getId()));
					OrderLineEntry lineEntry = omsHelper.getOrderLineEntry(releaseEntry.getOrderLines().get(0).getId().toString()); */

		RmsDP dp = new RmsDP();
		dp.CreateReturnRRQP_delete();
		dp.CreateReturnRRQP_data();

		String updateEmails="UPDATE order_release SET login = 'end2endautomation4@gmail.com'  WHERE order_id_fk=70041385;";
		DBUtilities.exUpdateQuery(updateEmails, "myntra_oms");

		String updateEmails2="UPDATE order_release SET email = 'end2endautomation4@gmail.com'  WHERE order_id_fk=70041385;";
		DBUtilities.exUpdateQuery(updateEmails2, "myntra_oms");

		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn("52697199", 1, ReturnMode.PICKUP, 27L);
		ReturnEntry returnEntry = returnResponse.getData().get(0);

		String returnId = returnEntry.getId().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);

		returnHelper.returnStatusProcessNew(returnEntry.getId().toString(), ReturnStatus.RRQP, ReturnStatus.RPI);

		returnHelper.returnStatusProcessNew(returnEntry.getId().toString(), ReturnStatus.RPI, ReturnStatus.RPF);
		ReturnResponse returnResponse4 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry4 = returnResponse4.getData().get(0);
		Assert.assertEquals(ReturnStatus.RPF, returnEntry4.getStatusCode());
		returnHelper.returnStatusProcessNew(returnEntry.getId().toString(), ReturnStatus.RPF, ReturnStatus.RJUP);

		Thread.sleep(5000);
		ReturnResponse returnResponse5 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry5 = returnResponse5.getData().get(0);

		Thread.sleep(20000);
		email.login(emailId, password);
		String html = email.fetchContent("Your Myntra return request cancelled");

		Assert.assertTrue(notifyHelper.getParsedData(html,RETURN_ONHOLDREJECTED.RETURN_NUMBER).contains(returnTrimmedString(returnEntry5.getId().toString())));
		log.info("\n Return Number :" +notifyHelper.getParsedData(html,RETURN_ONHOLDREJECTED.RETURN_NUMBER));

		Assert.assertEquals(notifyHelper.getParsedData(html,RETURN_ONHOLDREJECTED.PRODUCT_NAME),RETURN_ONHOLDREJECTED.STYLE);
		log.info("\n Product Name :" +notifyHelper.getParsedData(html,RETURN_ONHOLDREJECTED.PRODUCT_NAME));

		Assert.assertTrue(notifyHelper.getParsedData(html,RETURN_ONHOLDREJECTED.PRODUCT_DETAILS).contains(returnTrimmedString("Size: M")));
		Assert.assertTrue(notifyHelper.getParsedData(html,RETURN_ONHOLDREJECTED.PRODUCT_DETAILS).contains(returnTrimmedString(returnEntry5.getReturnRefundDetailsEntry().getRefundAmount().toString())));
		log.info("\n Product Detail :" +notifyHelper.getParsedData(html,RETURN_ONHOLDREJECTED.PRODUCT_DETAILS));

		//Return On-hold rejected SMS
		log.info("\n Return Pickup Failed SMS :" +notifyHelper.getSMS(mobilenumber,RETURN_ONHOLDREJECTED.RETURNCONFIRMED_SMS_ID));
		Assert.assertNotNull(notifyHelper.getSMS(mobilenumber,RETURN_ONHOLDREJECTED.RETURNCONFIRMED_SMS_ID));
		Assert.assertTrue(notifyHelper.getSMS(mobilenumber,RETURN_ONHOLDREJECTED.RETURNCONFIRMED_SMS_ID).contains(returnTrimmedString(returnEntry5.getId().toString())));


	}

	//Triggers order cancellation email and checks the contents
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression", "ExhaustiveRegression","RMSService" },
			enabled=true,priority=16, description = "Triggers order cancellation email and checks the contents")

	public void verifyOrderCancelledRelease() throws Exception {
		notifyHelper.deleteallSMSRecords(mobilenumber);
		notif.login_delete_emails(emailId, password);
		String orderID = e2e.createOrder(emailId, password, BLRAddressId, new String[] {ORDER_CONFIRM.SKU_QUANTITY}, "", false, false, false, "", false);
		OMSServiceHelper omsHelper = new OMSServiceHelper();
		OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
		OrderReleaseEntry releaseEntry = omsServiceHelper.getOrderReleaseEntry(omsServiceHelper.getReleaseId(orderEntry.getId().toString()));
		OrderLineEntry lineEntry = omsHelper.getOrderLineEntry(releaseEntry.getOrderLines().get(0).getId().toString());
		Thread.sleep(30000);

		omsServiceHelper.cancelOrderRelease(releaseEntry.getId().toString(), "1", emailId, "Test");


		Thread.sleep(20000);
		email.login(emailId, password);
		String html = email.fetchContent("Part of your Myntra order has been cancelled");

		Assert.assertTrue(Jsoup.parse(html).text().contains(releaseEntry.getDisplayStoreOrderId().toString()));
		Assert.assertFalse(Jsoup.parse(html).text().contains("$"));


	}

	//Triggers order cancellation email and checks the contents
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression", "ExhaustiveRegression","RMSService" },
			enabled=true,priority=17, description = "Triggers order cancellation email and checks the contents")

	public void verifyOrderCancelledLine() throws Exception {
		notifyHelper.deleteallSMSRecords(mobilenumber);
		notif.login_delete_emails(emailId, password);
		String orderID = e2e.createOrder(emailId, password, BLRAddressId, new String[] {ORDER_CONFIRM.SKU_QUANTITY}, "", false, false, false, "", false);
		OMSServiceHelper omsHelper = new OMSServiceHelper();
		OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
		OrderReleaseEntry releaseEntry = omsServiceHelper.getOrderReleaseEntry(omsServiceHelper.getReleaseId(orderEntry.getId().toString()));
		OrderLineEntry lineEntry = omsHelper.getOrderLineEntry(releaseEntry.getOrderLines().get(0).getId().toString());
		Thread.sleep(30000);

		//omsServiceHelper.cancelOrderRelease(releaseEntry.getId(), "1", emailId, "Test");
		omsServiceHelper.cancelLine(releaseEntry.getId().toString(), emailId, new String[] {""+lineEntry.getId() +":1"}, 1);


		Thread.sleep(20000);
		email.login(emailId, password);
		String html = email.fetchContent("Part of your Myntra order has been cancelled");

		Assert.assertTrue(Jsoup.parse(html).text().contains(releaseEntry.getDisplayStoreOrderId().toString()));
		Assert.assertFalse(Jsoup.parse(html).text().contains("$"));


	}

	//Triggers order cancellation email and checks the contents
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression", "ExhaustiveRegression","RMSService" },
			enabled=true,priority=18, description = "Triggers order cancellation email and checks the contents")

	public void verifyOrderRTO() throws Exception {
		notifyHelper.deleteallSMSRecords(mobilenumber);
		notif.login_delete_emails(emailId, password);
		String orderID = e2e.createOrder(emailId, password, BLRAddressId, new String[] {ORDER_CONFIRM.SKU_QUANTITY}, "", false, false, false, "", false);
		OMSServiceHelper omsHelper = new OMSServiceHelper();
		OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
		OrderReleaseEntry releaseEntry = omsServiceHelper.getOrderReleaseEntry(omsServiceHelper.getReleaseId(orderEntry.getId().toString()));
		OrderLineEntry lineEntry = omsHelper.getOrderLineEntry(releaseEntry.getOrderLines().get(0).getId().toString());
		Thread.sleep(30000);

		//omsServiceHelper.cancelOrderRelease(releaseEntry.getId(), "1", emailId, "Test");
		//omsServiceHelper.cancelLine(releaseEntry.getId(), emailId, new String[] {""+lineEntry.getId() +":1"}, 1);

		List<ReleaseEntry> releaseEntries = new ArrayList<>();
		releaseEntries.add(new ReleaseEntry.Builder(orderID, ReleaseStatus.RTO).build());
		ReleaseEntryList releaseEntryList= new ReleaseEntryList(releaseEntries);
		processRelease.processReleaseToStatus(releaseEntryList);

		//processRelease.processReleaseToStatus(new ReleaseEntry.Builder(omsServiceHelper.getReleaseId(orderID), ReleaseStatus.RTO).build());

		Thread.sleep(20000);
		email.login(emailId, password);
		String html = email.fetchContent("Undeliverable shipment returned to the fulfilment center");

		Assert.assertTrue(Jsoup.parse(html).text().contains(releaseEntry.getDisplayStoreOrderId().toString()));
		Assert.assertFalse(Jsoup.parse(html).text().contains("$"));


	}

	//Triggers Vat refund confirmation email & SMS and checks the contents
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression", "ExhaustiveRegression","RMSService" },
			enabled=true,priority=19, description = "Triggers Vat refund confirmation email and checks the contents")

	public void verifyVatRefund() throws Exception {
		notifyHelper.deleteallSMSRecords(mobilenumber);
		notif.login_delete_emails(emailId, password);
		String orderID = e2e.createOrder(emailId, password, BLRAddressId, new String[] {VAT_TEST.SKU_QUANTITY}, "snehaMoreThan20", false, false, false, "", false);
		OMSServiceHelper omsHelper = new OMSServiceHelper();
		OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
		OrderReleaseEntry releaseEntry = omsServiceHelper.getOrderReleaseEntry(omsServiceHelper.getReleaseId(orderEntry.getId().toString()));
		OrderLineEntry lineEntry = omsHelper.getOrderLineEntry(releaseEntry.getOrderLines().get(0).getId().toString());

		Thread.sleep(400000);
		email.login(emailId, password);
		String html = email.fetchContent("Balance Amount has been processed");

		Assert.assertTrue(notifyHelper.getParsedData(html,VAT_TEST.ORDER_ID).contains(returnTrimmedString(releaseEntry.getDisplayStoreOrderId().toString())));
		log.info("\n Order Id :" +notifyHelper.getParsedData(html,VAT_TEST.ORDER_ID));

		log.info("\n VAT Amount :" +notifyHelper.getParsedData(html,VAT_TEST.VAT_AMOUNT));
		System.out.println(lineEntry.getVatAdjustmentUnitRefund().toString());
		Assert.assertTrue(notifyHelper.getParsedData(html,VAT_TEST.VAT_AMOUNT).contains(returnTrimmedString(lineEntry.getVatAdjustmentUnitRefund().toString())));
		log.info("\n VAT Amount :" +notifyHelper.getParsedData(html,VAT_TEST.VAT_AMOUNT));

		Assert.assertEquals(notifyHelper.getParsedData(html,VAT_TEST.PRODUCT_NAME),VAT_TEST.STYLE);
		log.info("\n Product Name :" +notifyHelper.getParsedData(html,VAT_TEST.PRODUCT_NAME));

	}



	public String returnTrimmedString(String str){
		return str.toLowerCase().trim();

	}


}
	

