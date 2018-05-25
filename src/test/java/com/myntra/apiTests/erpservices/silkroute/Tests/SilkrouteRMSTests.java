package com.myntra.apiTests.erpservices.silkroute.Tests;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.HashMap;

import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.myntra.apiTests.end2end.End2EndHelper;
import com.myntra.apiTests.common.Constants.EnumSCM;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.erpservices.rms.RMSServiceHelper;
import com.myntra.apiTests.erpservices.rms.dp.RmsDP;
import com.myntra.apiTests.erpservices.silkroute.SilkRouteConstants;
import com.myntra.apiTests.erpservices.silkroute.SilkRouteServiceHelper;
import com.myntra.lordoftherings.Initialize;
import com.myntra.oms.client.entry.OrderEntry;
import com.myntra.test.commons.service.Svc;
import com.myntra.test.commons.testbase.BaseTest;
import org.apache.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.HashMap;

/**
 * @author santwana.samantray
 *
 */
public class SilkrouteRMSTests extends BaseTest {

	OMSServiceHelper omsServiceHelper = new OMSServiceHelper();

	Initialize init = new Initialize("/Data/configuration");
	Logger log = Logger.getLogger(SilkrouteRMSTests.class);
	RMSServiceHelper rmsServiceHelper = new RMSServiceHelper();
	SilkRouteServiceHelper silkRouteServiceHelper = new SilkRouteServiceHelper();
	private long defaultWaitTime = 10000L;

	private SoftAssert sft;

	// fk return creation
	// returnId, eventType, source, status,returnId,orderItemId,
	// source, status, action, quantity, orderDate,
	// createdDate,updatedDate,courierName, reason, subReason, trackingId,
	// shipmentId, comments, completionDate, replacementOrderItemId, productId,
	// listingId

	@BeforeClass
	public void testBeforeClass() throws ParseException, JAXBException, IOException {
		silkRouteServiceHelper.fkcreateOrder(SilkRouteConstants.FK3, "2933", "order_item_created", "2933", "APPROVED",
				"false", SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "1", "0",
				"LSTMOBDACGHGSMVG9VSIQLWV5", "NIKEBAPK99760", "200", "560001", "200", "36", "SH", "S");

		silkRouteServiceHelper.fkcreateOrder(SilkRouteConstants.FK4, "395218408727", "order_item_created",
				"395218408727", "APPROVED", "false", SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE,
				"1", "0", "LSTMOBDACGHGSMVG9VSIQLWV5", "NIKEBAPK99760", "200", "560001", "200", "36", "SH", "S");

		silkRouteServiceHelper.fkcreateOrder(SilkRouteConstants.FK4, "662177641129", "order_item_created",
				"662177641129", "APPROVED", "false", SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE,
				"1", "0", "LSTMOBDACGHGSMVG9VSIQLWV5", "NIKEBAPK99760", "200", "560001", "200", "36", "SH", "S");

	}

	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "CreateReturn", dataProviderClass = RmsDP.class, description = "SilkRoute Should Drop Tracking Number Update events for RTO Orders")
	public void silkRouteShouldDropRTOTrackingNumberEvent(String environment, String returnId, String eventType,
			String source,  String status, String orderItemId, String sourceline, String statusline,
			String action, String quantity,  String tracking,
			String response, String store_id) throws ParseException, IOException, JAXBException {

		silkRouteServiceHelper.fkcreateOrder(environment, orderItemId, "order_item_created", orderItemId, "APPROVED",
				"false", SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "1", "0",
				"LSTMOBDACGHGSMVG9VSIQLWV5", "NIKEBAPK99760", "200", "560001", "200", "36", "PK", "QD");

		End2EndHelper.sleep(defaultWaitTime);

		Svc createReturnResponse = rmsServiceHelper.invokeSilkRouteServiceCreateReturnAPI(environment,
				returnId, eventType, source, status, orderItemId, sourceline, statusline, action, quantity,
				 tracking);

		validateStatusInSilkroute("SilkrouteService createOrder API is not working", Integer.parseInt(response),
				createReturnResponse.getResponseStatus());

		End2EndHelper.sleep(defaultWaitTime);

		Svc svc = silkRouteServiceHelper.updateTrackingNumber(environment, orderItemId, returnId, "ML",
				"" + System.currentTimeMillis());
		validateStatusInSilkroute("Validate The Response Status", svc.getResponseStatus(), 200);
	}

	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "trackingNumberUpdateShouldThrowErrorIReturnIDNotPresent", dataProviderClass = RmsDP.class, description = "SilkRoute Should Drop Tracking Number Event Updates")
	public void trackingNumberUpdateShouldThrowErrorIReturnIDNotPresent(String store, String returnId,
			String orderItemId) throws ParseException, IOException, JAXBException {
		Svc svc = silkRouteServiceHelper.updateTrackingNumber(store, orderItemId, returnId, "ML",
				"" + System.currentTimeMillis());
		validateStatusInSilkroute("Validate The Response Status", svc.getResponseStatus(), 500);

	}

	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "CreateReturn", dataProviderClass = RmsDP.class, description = "Validate OrderTracking Number Update For Return")
	public void validateOrderTrackingNumberUpdateForReturn(String environment, String returnId, String eventType,
			String source,  String status, String orderItemId, String sourceline, String statusline,
			String action, String quantity,  String tracking,
			String response, String store_id) throws ParseException, IOException, JAXBException {

		sft = new SoftAssert();
		silkRouteServiceHelper.fkcreateOrder(environment, orderItemId, "order_item_created", orderItemId, "APPROVED",
				"false", SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "1", "0",
				"LSTMOBDACGHGSMVG9VSIQLWV5", "NIKEBAPK99760", "200", "560001", "200", "36", "SH", "S");

		End2EndHelper.sleep(defaultWaitTime);
		Svc createReturnResponse = rmsServiceHelper.invokeSilkRouteServiceCreateReturnAPI(environment,
				returnId, eventType, source, status, orderItemId, sourceline, statusline, action, quantity,
				 tracking);

		log.info("\nPrinting SilkrouteService createOrder API  response :\n\n"
				+ createReturnResponse.getResponseBody() + "\n");

		validateStatusInSilkroute("SilkrouteService createOrder API is not working", Integer.parseInt(response),
				createReturnResponse.getResponseStatus());
		String trackingId = "" + System.currentTimeMillis();
		Svc svc = silkRouteServiceHelper.updateTrackingNumber(environment, orderItemId, returnId, "EK", trackingId);
		validateStatusInSilkroute("Validate The Response Status", svc.getResponseStatus(), 200);
		// To-do Validate tracking number in Silkroute
		sft.assertEquals(trackingId, RMSServiceHelper.getSilkrouteReturnEntry(returnId).get("tracking_id").toString(),
				"Tracking number should be updated in Silkroute");
		sft.assertEquals("EK", RMSServiceHelper.getSilkrouteReturnEntry(returnId).get("courier").toString(),
				"Courier should be updated in Silkroute");
		sft.assertEquals(trackingId, RMSServiceHelper.getRmsReturnEntry(returnId).get("tracking_no").toString(),
				"Tracking no should be updated in RMS");

		sft.assertAll();
	}

	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "CreateReturn", dataProviderClass = RmsDP.class)
	public void CreateReturn(String environment, String returnId, String eventType, String source, 
			String status, String orderItemId, String sourceline, String statusline, String action, String quantity,
			 String tracking, String response, String store_id)
					throws ParseException, IOException, JAXBException {

		sft = new SoftAssert();
		silkRouteServiceHelper.fkcreateOrder(environment, orderItemId, "order_item_created", orderItemId, "APPROVED",
				"false", SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "1", "0",
				"LSTMOBDACGHGSMVG9VSIQLWV5", "NIKEBAPK99760", "200", "560001", "200", "36", "SH", "S");

		End2EndHelper.sleep(defaultWaitTime);
		Svc createReturnResponse = rmsServiceHelper.invokeSilkRouteServiceCreateReturnAPI(environment,
				returnId, eventType, source, status, orderItemId, sourceline, statusline, action, quantity,
				 tracking);
		String createOrderResponse = createReturnResponse.getResponseBody();
		log.info("\nPrinting SilkrouteService createOrder API  response :\n\n" + createOrderResponse + "\n");

		validateStatusInSilkroute("SilkrouteService createOrder API is not working", Integer.parseInt(response),
				createReturnResponse.getResponseStatus());

		// silkroute return entry
		validateSilkrouteReturnEntry(returnId, "Automation fk return___Automation fk return sub reason");

		// silkroute return_line entry
		validateSilkrouteReturnLineEntry(returnId, "Automation fk return___Automation fk return sub reason",
				"Automation fk return");
		// rms return entry
		validateRMSReturnEntry(returnId, EnumSCM.LPI, "Automation fk return___Automation fk return sub reason");
		// rms return_line entry
		validateRMSReturnLineEntry(returnId,EnumSCM.LPI, "31");

		sft.assertAll();
	}

	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "CreateReturnSourceCourier_Return", dataProviderClass = RmsDP.class, description = "Order should cancel in OMS for RTO update after Pack")
	public void createAndCompleteReturnSourceCourier_ReturnRTO_AfterPack(String environment, String returnId,
			String eventType, String source,  String status, String orderItemId, String sourceline,
			String statusline, String action, String quantity, 
			String tracking, String response, String store_id) throws ParseException, IOException, JAXBException {

		sft = new SoftAssert();
		silkRouteServiceHelper.fkcreateOrder(environment, orderItemId, "order_item_created", orderItemId, "APPROVED",
				"false", SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "1", "0",
				"LSTMOBDACGHGSMVG9VSIQLWV5", "NIKEBAPK99760", "200", "560001", "200", "36", "PK", "QD");

		// AssertJUnit.assertEquals(statusMessage, createOrderResponse);
		End2EndHelper.sleep(defaultWaitTime);

		Svc createReturnResponse = rmsServiceHelper.invokeSilkRouteServiceCreateReturnAPI(environment,
				returnId, eventType, source, status, orderItemId, sourceline, statusline, action, quantity,
				 tracking);
		End2EndHelper.sleep(defaultWaitTime);
		
		validateStatusInSilkroute("SilkrouteService createOrder API is not working", Integer.parseInt(response),
				createReturnResponse.getResponseStatus());

		createReturnResponse = rmsServiceHelper.invokeSilkRouteServiceCreateReturnAPI(environment, returnId,
				"return_completed", source, "return_completed", orderItemId, sourceline, statusline, action,
				quantity,  tracking);

		validateStatusInSilkroute("SilkrouteService createOrder API is not working", Integer.parseInt(response),
				createReturnResponse.getResponseStatus());

		validateSilkrouteRTOEntry(returnId, "Automation fk return___Automation fk return sub reason",
				"Automation fk return", EnumSCM.APPROVED);

		// Validate Status in OMS
		OrderEntry orderEntry = omsServiceHelper.getOrderEntryByStoreOrderID(orderItemId);
		sft.assertEquals(EnumSCM.F, orderEntry.getOrderReleases().iterator().next().getStatus(),
				"OMS Status should change to F in OMS ");
	}

	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "CreateReturnSourceCourier_Return", dataProviderClass = RmsDP.class)
	public void CreateReturnSourceCourier_ReturnRTO(String environment, String returnId, String eventType,
			String source,  String status, String orderItemId, String sourceline, String statusline,
			String action, String quantity,  String tracking,
			String response, String store_id) throws ParseException, IOException, JAXBException {

		silkRouteServiceHelper.fkcreateOrder(environment, orderItemId, "order_item_created", orderItemId, "APPROVED",
				"false", SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "1", "0",
				"LSTMOBDACGHGSMVG9VSIQLWV5", "NIKEBAPK99760", "200", "560001", "200", "36", "SH", "S");
		End2EndHelper.sleep(10000);
		Svc createOrderReqGen = rmsServiceHelper.invokeSilkRouteServiceCreateReturnAPI(environment,
				returnId, eventType, source, status, orderItemId, sourceline, statusline, action, quantity,
				 tracking);

		String createOrderResponse = createOrderReqGen.getResponseBody();
		log.info("\nPrinting SilkrouteService createOrder API response :\n\n" + createOrderResponse + "\n");
		log.info("\nPrinting SilkrouteService createOrder API  response :\n\n" + createOrderResponse + "\n");

		log.info("the repsonse is :" + createOrderReqGen.getResponseStatus());
		validateStatusInSilkroute("SilkrouteService createOrder API is not working", Integer.parseInt(response),
				createOrderReqGen.getResponseStatus());

		End2EndHelper.sleep(defaultWaitTime);
		validateSilkrouteRTOEntry(returnId, "Automation fk return___Automation fk return sub reason",
				"Automation fk return", EnumSCM.CREATED);
		sft.assertAll();
	}

	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "CreateSameReturnMultipleTimes", dataProviderClass = RmsDP.class)
	public void CreateSameReturnMultipleTimes(String environment, String returnId, String eventType, String source,
			 String status, String orderItemId, String sourceline, String statusline, String action,
			String quantity,  String tracking, String response,
			String store_id, String statusMessage) throws ParseException, UnsupportedEncodingException, JAXBException {

		sft = new SoftAssert();
		// CreateReturn First time
		Svc createReturnResponse = rmsServiceHelper.invokeSilkRouteServiceCreateReturnAPI(environment, returnId, eventType,
				source, status, orderItemId, sourceline, statusline, action, quantity, tracking);
		// Create Same Return second Time
		createReturnResponse = rmsServiceHelper.invokeSilkRouteServiceCreateReturnAPI(environment, returnId, eventType,
				source, status, orderItemId, sourceline, statusline, action, quantity,tracking);
		String createOrderResponse = createReturnResponse.getResponseBody();
		log.info("\nPrinting SilkrouteService createOrder API response :\n\n" + createOrderResponse + "\n");
		log.info("\nPrinting SilkrouteService createOrder API  response :\n\n" + createOrderResponse + "\n");
		log.info("the repsonse is :" + createReturnResponse);

		validateStatusInSilkroute("SilkrouteService createOrder API is not working", Integer.parseInt(response),
				createReturnResponse.getResponseStatus());

		sft.assertEquals(statusMessage, createOrderResponse, "Verify Response");
		sft.assertAll();
	}

	// returnId, eventType, source, status,returnId,orderItemId,
	// source, status, action, quantity, orderDate,
	// createdDate,updatedDate,courierName, reason, subReason, trackingId,
	// shipmentId, comments, completionDate, replacementOrderItemId, productId,
	// listingId

	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "ManualMarkReturnQCPassAfterCreation", dataProviderClass = RmsDP.class)
	public void ManualMarkReturnQCPassAfterCreation(String environment, String returnId, String eventType,
			String source,  String status, String orderItemId, String sourceline, String statusline,
			String action, String quantity,  String tracking,
			String response, String store_id) throws ParseException, UnsupportedEncodingException, JAXBException {

		Svc createReturnResponse = rmsServiceHelper.invokeSilkRouteServiceCreateReturnAPI(environment,
				returnId, eventType, source, status, orderItemId, sourceline, statusline, action, quantity,
				 tracking);
		String createOrderResponse = createReturnResponse.getResponseBody();
		log.info("\nPrinting SilkrouteService createOrder API  response :\n\n" + createOrderResponse + "\n");

		validateStatusInSilkroute("SilkrouteService createOrder API is not working", Integer.parseInt(response),
				createReturnResponse.getResponseStatus());

	}

	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "ManualMarkReturnQCPassAfterCompletion", dataProviderClass = RmsDP.class)
	public void ManualMarkReturnQCPassAfterCompletion(String environment, String returnId, String eventType,
			String source,  String status, String orderItemId, String sourceline, String statusline,
			String action, String quantity,  String tracking,
			String response, String store_id) throws ParseException, UnsupportedEncodingException, JAXBException {

		Svc createReturnResponse = rmsServiceHelper.invokeSilkRouteServiceCreateReturnAPI(environment,
				returnId, eventType, source, status, orderItemId, sourceline, statusline, action, quantity,
				 tracking);
		String createOrderResponse = createReturnResponse.getResponseBody();
		log.info("\nPrinting SilkrouteService createReturn API  response :\n\n" + createOrderResponse + "\n");

		validateStatusInSilkroute("SilkrouteService createOrder API is not working", Integer.parseInt(response),
				createReturnResponse.getResponseStatus());

	}

	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "CreateandDeclineReturn", dataProviderClass = RmsDP.class)
	public void CreateandDeclineReturn(String environment, String returnId, String eventType, String source,
			String status, String orderItemId, String sourceline, String statusline, String action,
			String quantity, String tracking, String response,
			String store_id) throws ParseException, IOException, JAXBException {

		sft = new SoftAssert();
		silkRouteServiceHelper.fkcreateOrder(environment, orderItemId, "order_item_created", orderItemId, "APPROVED",
				"false", SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "1", "0",
				"LSTMOBDACGHGSMVG9VSIQLWV5", "NIKEBAPK99760", "200", "560001", "200", "36", "SH", "S");
		End2EndHelper.sleep(defaultWaitTime);
		Svc createReturnResponse = rmsServiceHelper.invokeSilkRouteServiceCreateReturnAPI(environment,
				returnId, eventType, source, status, orderItemId, sourceline, statusline, action, quantity,
				 tracking);
		String createOrderResponse = createReturnResponse.getResponseBody();

		log.info("\nPrinting SilkrouteService createOrder API  response :\n\n" + createOrderResponse + "\n");

		validateStatusInSilkroute("SilkrouteService createOrder API is not working", Integer.parseInt(response),
				createReturnResponse.getResponseStatus());

		// silkroute return entry
		validateSilkrouteReturnEntry(returnId, "Automation fk return___Automation fk return sub reason");

		// silkroute return_line entry
		validateSilkrouteReturnLineEntry(returnId, "Automation fk return___Automation fk return sub reason",
				"Automation fk return");

		// rms return entry
		validateRMSReturnEntry(returnId, EnumSCM.LPI, "Automation fk return___Automation fk return sub reason");

		// rms return_line entry
		validateRMSReturnLineEntry(returnId, EnumSCM.LPI, "31");

		Svc completereturn = rmsServiceHelper.invokeSilkRouteServiceCompleteReturnAPI(environment,
				returnId, "return_cancelled", source, status, orderItemId);
		String completeReturnResponse = completereturn.getResponseBody();
		log.info("\nPrinting SilkrouteService createOrder API response :\n\n" + completeReturnResponse + "\n");
		log.info("\nPrinting SilkrouteService createOrder API  response :\n\n" + completeReturnResponse + "\n");
		log.info("the repsonse is :" + completereturn);

		validateStatusInSilkroute("SilkrouteService createOrder API is not working", Integer.parseInt(response),
				completereturn.getResponseStatus());

		validateRMSReturnEntry(returnId, EnumSCM.DEC, "Automation fk return");
		validateRMSReturnLineEntry(returnId, EnumSCM.DEC, "31");
		sft.assertAll();

	}

	// returnId, eventType, source, status,returnId,orderItemId,
	// source, status, action, quantity, orderDate, createdDate,updatedDate
	// ,courierName, reason, subReason, trackingId, shipmentId, comments,
	// completionDate, replacementOrderItemId, productId, listingId

	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "ReturnCreationDeclineAndMarkComplete", dataProviderClass = RmsDP.class)
	public void ReturnCreationDeclineAndMarkComplete(String environment, String returnId, String eventType,
			String source,  String status, String orderItemId, String sourceline, String statusline,
			String action, String quantity,  String tracking,
			String response, String store_id) throws ParseException, IOException, JAXBException {

		sft = new SoftAssert();
		silkRouteServiceHelper.fkcreateOrder(environment, orderItemId, "order_item_created", orderItemId, "APPROVED",
				"false", SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "1", "0",
				"LSTMOBDACGHGSMVG9VSIQLWV5", "NIKEBAPK99760", "200", "560001", "200", "36", "SH", "S");
		End2EndHelper.sleep(defaultWaitTime);
		Svc createReturnResponse = rmsServiceHelper.invokeSilkRouteServiceCreateReturnAPI(environment,
				returnId, eventType, source, status, orderItemId, sourceline, statusline, action, quantity,
				 tracking);
		String createOrderResponse = createReturnResponse.getResponseBody();
		log.info("\nPrinting SilkrouteService createOrder API response :\n\n" + createOrderResponse + "\n");
		log.info("\nPrinting SilkrouteService createOrder API  response :\n\n" + createOrderResponse + "\n");
		log.info("the repsonse is :" + createReturnResponse);

		validateStatusInSilkroute("SilkrouteService createOrder API is not working", Integer.parseInt(response),
				createReturnResponse.getResponseStatus());

		// silkroute return entry
		validateSilkrouteReturnEntry(returnId, "Automation fk return___Automation fk return sub reason");

		// silkroute return_line entry
		validateSilkrouteReturnLineEntry(returnId, "Automation fk return___Automation fk return sub reason",
				"Automation fk return");

		// rms return entry
		validateRMSReturnEntry(returnId, EnumSCM.LPI, "Automation fk return___Automation fk return sub reason");

		// rms return_line entry
		validateRMSReturnLineEntry(returnId, EnumSCM.LPI, "31");

		Svc completereturn = rmsServiceHelper.invokeSilkRouteServiceCompleteReturnAPI(environment,
				returnId, "return_cancelled", source, status, orderItemId);
		String completeReturnResponse = completereturn.getResponseBody();
		log.info("\nPrinting SilkrouteService createOrder API response :\n\n" + completeReturnResponse + "\n");
		log.info("\nPrinting SilkrouteService createOrder API  response :\n\n" + completeReturnResponse + "\n");
		log.info("the repsonse is :" + completereturn);

		validateStatusInSilkroute("SilkrouteService createOrder API is not working", Integer.parseInt(response),
				completereturn.getResponseStatus());
		validateRMSReturnEntry(returnId, EnumSCM.DEC, "Automation fk return___Automation fk return sub reason");

		Svc completereturn2 = rmsServiceHelper.invokeSilkRouteServiceCompleteReturnAPI(environment,
				returnId, "return_completed", source, status, orderItemId);
		String completeReturnResponse2 = completereturn2.getResponseBody();
		log.info("\nPrinting SilkrouteService createOrder API response :\n\n" + completeReturnResponse2 + "\n");
		log.info("\nPrinting SilkrouteService createOrder API  response :\n\n" + completeReturnResponse2 + "\n");
		log.info("the repsonse is :" + completereturn2);

		validateStatusInSilkroute("SilkrouteService createOrder API is not working", Integer.parseInt(response),
				completereturn2.getResponseStatus());
		validateRMSReturnEntry(returnId, EnumSCM.DEC, "Automation fk return___Automation fk return sub reason");
		sft.assertAll();
	}

	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "CreateReturnForOrderNotpresentinSilkroute", dataProviderClass = RmsDP.class)
	public void CreateReturnForOrderNotpresentinSilkroute(String environment, String returnId, String eventType,
			String source,  String status, String orderItemId, String sourceline, String statusline,
			String action, String quantity,  String tracking,
			String response, String store_id, String statusMessage) throws ParseException, UnsupportedEncodingException, JAXBException {

		sft = new SoftAssert();
		Svc createReturnResponse = rmsServiceHelper.invokeSilkRouteServiceCreateReturnAPI(environment,
				returnId, eventType, source, status, orderItemId, sourceline, statusline, action, quantity,
				 tracking);
		String createOrderResponse = createReturnResponse.getResponseBody();
		log.info("\nPrinting SilkrouteService createOrder API response :\n\n" + createOrderResponse + "\n");
		log.info("\nPrinting SilkrouteService createOrder API  response :\n\n" + createOrderResponse + "\n");
		log.info("the repsonse is :" + createReturnResponse);

		validateStatusInSilkroute("SilkrouteService createOrder API is not working", Integer.parseInt(response),
				createReturnResponse.getResponseStatus());

		sft.assertEquals(statusMessage, createOrderResponse, "Verify response");
		verifyReturnNotAvailableInSilkroute(returnId);
		sft.assertAll();
	}

	// returnId, eventType, source, status,returnId,orderItemId,
	// source, status, action, quantity, orderDate,
	// createdDate,updatedDate,courierName, reason, subReason, trackingId,
	// shipmentId, comments, completionDate, replacementOrderItemId, productId,
	// listingId

	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "CreateReturnWithQtyNull", dataProviderClass = RmsDP.class)
	public void CreateReturnWithQtyNull(String environment, String returnId, String eventType, String source,
			 String status, String orderItemId, String sourceline, String statusline, String action,
			String quantity,  String tracking, String response,
			String store_id, String statusMessage) throws ParseException, UnsupportedEncodingException, JAXBException {
		sft = new SoftAssert();
		Svc createReturnResponse = rmsServiceHelper.invokeSilkRouteServiceCreateReturnAPI(environment,
				returnId, eventType, source, status, orderItemId, sourceline, statusline, action, quantity,
				 tracking);
		String createOrderResponse = createReturnResponse.getResponseBody();
		log.info("\nPrinting SilkrouteService createOrder API response :\n\n" + createOrderResponse + "\n");
		log.info("\nPrinting SilkrouteService createOrder API  response :\n\n" + createOrderResponse + "\n");
		log.info("the repsonse is :" + createReturnResponse);

		validateStatusInSilkroute("SilkrouteService createOrder API is not working", Integer.parseInt(response),
				createReturnResponse.getResponseStatus());

		sft.assertEquals(statusMessage, createOrderResponse, "Verify response");
		verifyReturnNotAvailableInSilkroute(returnId);
		sft.assertAll();
	}

	// returnId, eventType, source, status,returnId,orderItemId,
	// source, status, action, quantity, orderDate,
	// createdDate,updatedDate,courierName, reason, subReason, trackingId,
	// shipmentId, comments, completionDate, replacementOrderItemId, productId,
	// listingId

	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "CreateReturnWithReturnIdNull", dataProviderClass = RmsDP.class)
	public void CreateReturnWithReturnIdNull(String environment, String returnId, String eventType, String source,
			 String status, String orderItemId, String sourceline, String statusline, String action,
			String quantity,  String tracking, String response,
			String store_id, String statusMessage) throws ParseException, UnsupportedEncodingException, JAXBException {
		sft = new SoftAssert();
		Svc createReturnResponse = rmsServiceHelper.invokeSilkRouteServiceCreateReturnAPI(environment,
				returnId, eventType, source, status, orderItemId, sourceline, statusline, action, quantity,
				 tracking);
		String createOrderResponse = createReturnResponse.getResponseBody();
		log.info("\nPrinting SilkrouteService createOrder API response :\n\n" + createOrderResponse + "\n");
		log.info("\nPrinting SilkrouteService createOrder API  response :\n\n" + createOrderResponse + "\n");
		log.info("the repsonse is :" + createReturnResponse);

		validateStatusInSilkroute("SilkrouteService createOrder API is not working", Integer.parseInt(response),
				createReturnResponse.getResponseStatus());

		sft.assertEquals(statusMessage, createOrderResponse, "Verify Response");
		sft.assertAll();
	}

	// returnId, eventType, source, status,returnId,orderItemId,
	// source, status, action, quantity, orderDate,
	// createdDate,updatedDate,courierName, reason, subReason, trackingId,
	// shipmentId, comments, completionDate, replacementOrderItemId, productId,
	// listingId

	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "CreateReturnWithOrderItemIdNull", dataProviderClass = RmsDP.class)
	public void CreateReturnWithOrderItemIdNull(String environment, String returnId, String eventType, String source,
			 String status, String orderItemId, String sourceline, String statusline, String action,
			String quantity,  String tracking, String response,
			String store_id, String statusMessage) throws ParseException, UnsupportedEncodingException, JAXBException {
		sft = new SoftAssert();
		Svc createReturnResponse = rmsServiceHelper.invokeSilkRouteServiceCreateReturnAPI(environment,
				returnId, eventType, source, status, orderItemId, sourceline, statusline, action, quantity,
				 tracking);
		String createOrderResponse = createReturnResponse.getResponseBody();
		log.info("\nPrinting SilkrouteService createOrder API response :\n\n" + createOrderResponse + "\n");
		log.info("\nPrinting SilkrouteService createOrder API  response :\n\n" + createOrderResponse + "\n");
		log.info("the repsonse is :" + createReturnResponse);

		validateStatusInSilkroute("SilkrouteService createOrder API is not working", Integer.parseInt(response),
				createReturnResponse.getResponseStatus());

		sft.assertEquals(statusMessage, createOrderResponse, "Verify Response");
		verifyReturnNotAvailableInSilkroute(returnId);
		sft.assertAll();
	}

	// returnId, eventType, source, status,returnId,orderItemId,
	// source, status, action, quantity, orderDate,
	// createdDate,updatedDate,courierName, reason, subReason, trackingId,
	// shipmentId, comments, completionDate, replacementOrderItemId, productId,
	// listingId

	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "CreateReturnWithEventTypeNull", dataProviderClass = RmsDP.class)
	public void CreateReturnWithEventTypeNull(String environment, String returnId, String eventType, String source,
			 String status, String orderItemId, String sourceline, String statusline, String action,
			String quantity,  String tracking, String response,
			String store_id, String statusMessage) throws ParseException, UnsupportedEncodingException, JAXBException {

		sft = new SoftAssert();
		Svc createReturnResponse = rmsServiceHelper.invokeSilkRouteServiceCreateReturnAPI(environment,
				returnId, eventType, source, status, orderItemId, sourceline, statusline, action, quantity,
				 tracking);
		String createOrderResponse = createReturnResponse.getResponseBody();
		log.info("\nPrinting SilkrouteService createOrder API response :\n\n" + createOrderResponse + "\n");
		log.info("\nPrinting SilkrouteService createOrder API  response :\n\n" + createOrderResponse + "\n");
		log.info("the repsonse is :" + createReturnResponse);

		validateStatusInSilkroute("SilkrouteService createOrder API is not working", Integer.parseInt(response),
				createReturnResponse.getResponseStatus());

		sft.assertEquals(statusMessage, createOrderResponse, "Verify Response");
		verifyReturnNotAvailableInSilkroute(returnId);
		sft.assertAll();
	}

	// returnId, eventType, source, status,returnId,orderItemId,
	// source, status, action, quantity, orderDate,
	// createdDate,updatedDate,courierName, reason, subReason, trackingId,
	// shipmentId, comments, completionDate, replacementOrderItemId, productId,
	// listingId

	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "CreateReturnWithTrackingNumberNull", dataProviderClass = RmsDP.class)
	public void CreateReturnWithTrackingNumberNull(String environment, String returnId, String eventType, String source,
			 String status, String orderItemId, String sourceline, String statusline, String action,
			String quantity,  String tracking, String response,
			String store_id, String statusMessage) throws ParseException, UnsupportedEncodingException, JAXBException {

		sft = new SoftAssert();
		Svc createReturnResponse;
		createReturnResponse = rmsServiceHelper.invokeSilkRouteServiceCreateReturnAPI(environment, returnId, eventType,
				source, status, orderItemId, sourceline, statusline, action, quantity, tracking);
		String createOrderResponse = createReturnResponse.getResponseBody();
		log.info("\nPrinting SilkrouteService createOrder API response :\n\n" + createOrderResponse + "\n");
		log.info("\nPrinting SilkrouteService createOrder API  response :\n\n" + createOrderResponse + "\n");
		log.info("the repsonse is :" + createReturnResponse);

		validateStatusInSilkroute("SilkrouteService createOrder API is not working", Integer.parseInt(response),
				createReturnResponse.getResponseStatus());

		sft.assertEquals(statusMessage, createOrderResponse, "Verify Response");
		verifyReturnCreatedInSilkroute(returnId);
		sft.assertAll();
	}

	// returnId, eventType, source, status,returnId,orderItemId,
	// source, status, action, quantity, orderDate,
	// createdDate,updatedDate,courierName, reason, subReason, trackingId,
	// shipmentId, comments, completionDate, replacementOrderItemId, productId,
	// listingId

	private void verifyReturnCreatedInSilkroute(String returnId) {
		// TODO Auto-generated method stub
		sft = new SoftAssert();
		HashMap<String, Object> hm_silkroute_return = RMSServiceHelper.getSilkrouteReturnEntry(returnId);
		sft.assertNotNull(hm_silkroute_return, "Verify Return is created in Silkroute");
		sft.assertAll();

	}

	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "CreateReturnWithSourceNull", dataProviderClass = RmsDP.class)
	public void CreateReturnWithSourceNull(String environment, String returnId, String eventType, String source,
			 String status, String orderItemId, String sourceline, String statusline, String action,
			String quantity,  String tracking, String response,
			String store_id, String statusMessage) throws ParseException, UnsupportedEncodingException, JAXBException {
		sft = new SoftAssert();
		Svc createReturnResponse = rmsServiceHelper.invokeSilkRouteServiceCreateReturnAPI(environment,
				returnId, eventType, source, status, orderItemId, sourceline, statusline, action, quantity,
				 tracking);
		String createOrderResponse = createReturnResponse.getResponseBody();
		log.info("\nPrinting SilkrouteService createOrder API response :\n\n" + createOrderResponse + "\n");
		log.info("\nPrinting SilkrouteService createOrder API  response :\n\n" + createOrderResponse + "\n");
		log.info("the repsonse is :" + createReturnResponse);

		validateStatusInSilkroute("SilkrouteService createOrder API is not working", Integer.parseInt(response),
				createReturnResponse.getResponseStatus());

		sft.assertEquals(statusMessage, createOrderResponse, "Verify response");
		verifyReturnNotAvailableInSilkroute(returnId);
		sft.assertAll();
	}

	// returnId, eventType, source, status,returnId,orderItemId,
	// source, status, action, quantity, orderDate,
	// createdDate,updatedDate,courierName, reason, subReason, trackingId,
	// shipmentId, comments, completionDate, replacementOrderItemId, productId,
	// listingId

	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "CreateReturnWithSourceLineNull", dataProviderClass = RmsDP.class)
	public void CreateReturnWithSourceLineNull(String environment, String returnId, String eventType, String source,
			 String status, String orderItemId, String sourceline, String statusline, String action,
			String quantity,  String tracking, String response,
			String store_id, String statusMessage) throws ParseException, UnsupportedEncodingException, JAXBException {

		sft = new SoftAssert();
		Svc createReturnResponse = rmsServiceHelper.invokeSilkRouteServiceCreateReturnAPI(environment,
				returnId, eventType, source, status, orderItemId, sourceline, statusline, action, quantity,
				 tracking);
		String createOrderResponse = createReturnResponse.getResponseBody();
		log.info("\nPrinting SilkrouteService createOrder API response :\n\n" + createOrderResponse + "\n");
		log.info("\nPrinting SilkrouteService createOrder API  response :\n\n" + createOrderResponse + "\n");
		log.info("the repsonse is :" + createReturnResponse);

		validateStatusInSilkroute("SilkrouteService createOrder API is not working", Integer.parseInt(response),
				createReturnResponse.getResponseStatus());

		sft.assertEquals(statusMessage, createOrderResponse, "Verify Response");
		verifyReturnNotAvailableInSilkroute(returnId);
		sft.assertAll();
	}

	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "MakrReturnCompleteBeforeCreation", dataProviderClass = RmsDP.class)
	public void MakrReturnCompleteBeforeCreation(String environment, String returnId, String eventType, String source,
			 String status, String orderItemId, String response, String store_id, String statusMessage)
					throws ParseException, UnsupportedEncodingException {
		sft = new SoftAssert();
		Svc completeReturn = rmsServiceHelper.invokeSilkRouteServiceCompleteReturnAPI(environment,
				returnId, eventType, source, status, orderItemId);
		String createOrderResponse = completeReturn.getResponseBody();
		log.info("\nPrinting SilkrouteService createOrder API response :\n\n" + createOrderResponse + "\n");
		log.info("\nPrinting SilkrouteService createOrder API  response :\n\n" + createOrderResponse + "\n");

		log.info("the repsonse is :" + completeReturn);
		validateStatusInSilkroute("SilkrouteService createOrder API is not working", Integer.parseInt(response),
				completeReturn.getResponseStatus());

		sft.assertEquals(statusMessage, createOrderResponse, "Verify response");

		verifyReturnNotAvailableInSilkroute(returnId);
		sft.assertAll();

	}

	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "MakrReturnDeclineBeforeCreation", dataProviderClass = RmsDP.class)
	public void MakrReturnDeclineBeforeCreation(String environment, String returnId, String eventType, String source,
			 String status, String orderItemId, String response, String store_id, String statusMessage)
					throws ParseException, UnsupportedEncodingException {

		sft = new SoftAssert();
		Svc completeReturn = rmsServiceHelper.invokeSilkRouteServiceCompleteReturnAPI(environment,
				returnId, eventType, source, status, orderItemId);
		String createOrderResponse = completeReturn.getResponseBody();
		log.info("\nPrinting SilkrouteService createOrder API response :\n\n" + createOrderResponse + "\n");
		log.info("\nPrinting SilkrouteService createOrder API  response :\n\n" + createOrderResponse + "\n");

		log.info("the repsonse is :" + completeReturn);
		validateStatusInSilkroute("SilkrouteService createOrder API is not working", Integer.parseInt(response),
				completeReturn.getResponseStatus());

		sft.assertEquals(statusMessage, createOrderResponse, "Verify Response");
		// Verify Return Not Available in Silkroute
		verifyReturnNotAvailableInSilkroute(returnId);

	}

	private void verifyReturnNotAvailableInSilkroute(String returnId) {
		// TODO Auto-generated method stub
		sft = new SoftAssert();
		HashMap<String, Object> hm_silkroute_return = RMSServiceHelper.getSilkrouteReturnEntry(returnId);
		sft.assertNull(hm_silkroute_return, "Verify Return is not available in Silkroute");
		sft.assertAll();
	}

	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "CreateRTOMarkComplete", dataProviderClass = RmsDP.class)
	public void CreateRTOMarkComplete(String environment, String returnId, String eventType, String source,
			 String status, String orderItemId, String sourceline, String statusline, String action,
			String quantity,  String tracking, String response,
			String store_id) throws ParseException, IOException, JAXBException {

		silkRouteServiceHelper.fkcreateOrder(environment, orderItemId, "order_item_created", orderItemId, "APPROVED",
				"false", SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "1", "0",
				"LSTMOBDACGHGSMVG9VSIQLWV5", "NIKEBAPK99760", "200", "560001", "200", "36", "SH", "S");
		End2EndHelper.sleep(defaultWaitTime);
		Svc createReturnResponse = rmsServiceHelper.invokeSilkRouteServiceCreateReturnAPI(environment,
				returnId, eventType, source, status, orderItemId, sourceline, statusline, action, quantity,
				 tracking);
		String createOrderResponse = createReturnResponse.getResponseBody();
		log.info("\nPrinting SilkrouteService createOrder API  response :\n\n" + createOrderResponse + "\n");

		validateStatusInSilkroute("SilkrouteService createOrder API is not working", Integer.parseInt(response),
				createReturnResponse.getResponseStatus());

		validateSilkrouteRTOEntry(returnId, "Automation fk return___Automation fk return sub reason",
				"Automation fk return", EnumSCM.CREATED);

		Svc completereturn = rmsServiceHelper.invokeSilkRouteServiceCompleteReturnAPI(environment,
				returnId, "return_completed", source, status, orderItemId);
		String completeReturnResponse = completereturn.getResponseBody();
		log.info("\nPrinting SilkrouteService createOrder API  response :\n\n" + completeReturnResponse + "\n");

		validateStatusInSilkroute("SilkrouteService createOrder API is not working", Integer.parseInt(response),
				completereturn.getResponseStatus());
		HashMap<String, Object> hm_oms_rto = RMSServiceHelper.getRTOEntry(orderItemId);
		String oms_status = "" + hm_oms_rto.get("status_code");

		sft.assertEquals(EnumSCM.RTO, oms_status, "checking status code in oms for rto");
		sft.assertAll();

	}

	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "CreateRTOMarkDecline", dataProviderClass = RmsDP.class)
	public void CreateRTOMarkDecline(String environment, String returnId, String eventType, String source,
			 String status, String orderItemId, String sourceline, String statusline, String action,
			String quantity,  String tracking, String response,
			String store_id) throws ParseException, IOException, JAXBException {

		sft = new SoftAssert();
		silkRouteServiceHelper.fkcreateOrder(environment, orderItemId, "order_item_created", orderItemId, "APPROVED",
				"false", SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "1", "0",
				"LSTMOBDACGHGSMVG9VSIQLWV5", "NIKEBAPK99760", "200", "560001", "200", "36", "SH", "S");
		End2EndHelper.sleep(defaultWaitTime);
		Svc createReturnResponse = rmsServiceHelper.invokeSilkRouteServiceCreateReturnAPI(environment,
				returnId, eventType, source, status, orderItemId, sourceline, statusline, action, quantity,
				 tracking);
		String createOrderResponse = createReturnResponse.getResponseBody();

		log.info("\nPrinting SilkrouteService createOrder API  response :\n\n" + createOrderResponse + "\n");

		validateStatusInSilkroute("SilkrouteService createOrder API is not working", Integer.parseInt(response),
				createReturnResponse.getResponseStatus());
		// AssertJUnit.assertEquals(statusMessage, createOrderResponse);

		validateSilkrouteRTOEntry(returnId, "Automation fk return___Automation fk return sub reason",
				"Automation fk return", EnumSCM.CREATED);
		Svc completereturn = rmsServiceHelper.invokeSilkRouteServiceCompleteReturnAPI(environment,
				returnId, "return_cancelled", source, status, orderItemId);
		String completeReturnResponse = completereturn.getResponseBody();
		log.info("\nPrinting SilkrouteService createOrder API  response :\n\n" + completeReturnResponse + "\n");

		validateStatusInSilkroute("SilkrouteService createOrder API is not working", Integer.parseInt(response),
				completereturn.getResponseStatus());
		HashMap<String, Object> hm_oms_rto = RMSServiceHelper.getRTOEntry(orderItemId);
		String oms_status = "" + hm_oms_rto.get("status_code");

		sft.assertEquals(EnumSCM.SH.toString(), oms_status, "checking status code in oms for rto");
		sft.assertAll();

	}

	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "CreateReturn", dataProviderClass = RmsDP.class)
	public void CreateReturnAndUpdateTracking(String environment, String returnId, String eventType, String source,
			 String status, String storeOrderId, String sourceline, String statusline, String action,
			String quantity,  String tracking, String response,
			String store_id) throws ParseException, IOException, JAXBException {

		silkRouteServiceHelper.fkcreateOrder(environment, storeOrderId, "order_item_created", storeOrderId, "APPROVED",
				"false", SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "1", "0",
				"LSTMOBDACGHGSMVG9VSIQLWV5", "NIKEBAPK99760", "200", "560001", "200", "36", "SH", "S");

		End2EndHelper.sleep(defaultWaitTime);
		Svc createReturnResponse = rmsServiceHelper.invokeSilkRouteServiceCreateReturnAPI(environment,
				returnId, eventType, source, status, storeOrderId, sourceline, statusline, action, quantity,
				 tracking);
		String createOrderResponse = createReturnResponse.getResponseBody();
		log.info("\nPrinting SilkrouteService createOrder API  response :\n\n" + createOrderResponse + "\n");

		validateStatusInSilkroute("SilkrouteService createOrder API is not working", Integer.parseInt(response),
				createReturnResponse.getResponseStatus());

		// silkroute return entry
		validateSilkrouteReturnEntry(returnId, "Automation fk return___Automation fk return sub reason");

		// silkroute return_line entry
		validateSilkrouteReturnLineEntry(returnId, "Automation fk return___Automation fk return sub reason",
				"Automation fk return");
		// rms return entry
		validateRMSReturnEntry(returnId, EnumSCM.LPI, "Automation fk return___Automation fk return sub reason");
		// rms return_line entry
		validateRMSReturnLineEntry(returnId, EnumSCM.LPI, "31");

		createReturnResponse = rmsServiceHelper.invokeSilkRouteServiceCreateReturnAPI(environment, returnId,
				"return_item_tracking_id_update", source, status, storeOrderId, sourceline, statusline,
				action, quantity,  "track98765");
		// verify Tracking number is updated in silkroute
		verifyTrackingNumberUpdate(returnId, storeOrderId, "track98765");

	}

	private void verifyTrackingNumberUpdate(String returnId, String storeOrderId, String expectedTrackingId)
			throws UnsupportedEncodingException, JAXBException {
		// TODO Auto-generated method stub
		sft = new SoftAssert();
		String omsTrackingNumber = omsServiceHelper.getOrderEntryByStoreOrderID(storeOrderId).getOrderReleases().get(0)
				.getTrackingNo();
		String rmsTrackingNumber = RMSServiceHelper.getRmsReturnEntry(returnId).get("tracking_no").toString();
		String silkrouteTrackingNumber = RMSServiceHelper.getSilkrouteReturnEntry(returnId).get("tracking_id")
				.toString();
		sft.assertEquals(omsTrackingNumber, expectedTrackingId,
				"tracking number should be updated in OMS Actual:" + omsTrackingNumber);
		sft.assertEquals(rmsTrackingNumber, expectedTrackingId,
				"tracking number should be updated in OMS Actual:" + rmsTrackingNumber);
		sft.assertEquals(silkrouteTrackingNumber, expectedTrackingId,
				"tracking number should be updated in OMS Actual:" + silkrouteTrackingNumber);
	}

	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "CreateReturnSourceCourier_Return", dataProviderClass = RmsDP.class, description = "Order should cancel in OMS for RTO update after Pack")
	public void createAndCompleteReturnSourceCourier_ReturnRTO_AfterShipped(String environment, String returnId,
			String eventType, String source,  String status, String storeOrderId, String sourceline,
			String statusline, String action, String quantity, 
			String tracking, String response, String store_id) throws ParseException, IOException, JAXBException {

		sft = new SoftAssert();
		silkRouteServiceHelper.fkcreateOrder(environment, storeOrderId, "order_item_created", storeOrderId, "APPROVED",
				"false", SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "1", "0",
				"LSTMOBDACGHGSMVG9VSIQLWV5", "NIKEBAPK99760", "200", "560001", "200", "36", "SH", "D");

		End2EndHelper.sleep(defaultWaitTime);

		Svc createReturnResponse = rmsServiceHelper.invokeSilkRouteServiceCreateReturnAPI(environment,
				returnId, eventType, source, status, storeOrderId, sourceline, statusline, action, quantity,
				 tracking);
		End2EndHelper.sleep(defaultWaitTime);

		validateStatusInSilkroute("SilkrouteService createOrder API is not working", Integer.parseInt(response),
				createReturnResponse.getResponseStatus());

		createReturnResponse = rmsServiceHelper.invokeSilkRouteServiceCreateReturnAPI(environment, returnId,
				"return_completed", source, "return_completed", storeOrderId, sourceline, statusline, action,
				quantity,  tracking);

		String createOrderResponse = createReturnResponse.getResponseBody();
		End2EndHelper.sleep(defaultWaitTime);
		validateStatusInSilkroute("SilkrouteService createOrder API is not working", Integer.parseInt(response),
				createReturnResponse.getResponseStatus());

		validateSilkrouteRTOEntry(returnId, "Automation fk return___Automation fk return sub reason",
				"Automation fk return", EnumSCM.APPROVED);

		// Validate Status in OMS
		validateOMSOrderReleaseEntry(storeOrderId, EnumSCM.RTO.toString());
		sft.assertAll();
	}

	/**
	 * @param message
	 * @param statusCode
	 * @param expectedStatusCode
	 */
	public void validateStatusInSilkroute(String message, int statusCode, int expectedStatusCode) {
		sft = new SoftAssert();
		sft.assertTrue(silkRouteServiceHelper.verifyResponseInSilkRoute(statusCode, expectedStatusCode, 10), message);
		sft.assertAll();
	}

	public void validateSilkrouteReturnEntry(String returnId, String returnCommentExpected) {
		sft = new SoftAssert();
		HashMap<String, Object> hm_silkroute_return = RMSServiceHelper.getSilkrouteReturnEntry(returnId);
		String store_return_id = "" + hm_silkroute_return.get("store_return_id");
		String return_comment = "" + hm_silkroute_return.get("comment");
		sft.assertEquals(returnCommentExpected, return_comment, "silkroute return  comment");
		sft.assertEquals(returnId, store_return_id, "silkroute return  store_release_id");
		sft.assertAll();
	}

	public void validateSilkrouteReturnLineEntry(String returnId, String returnReasonExpected,
			String returnCommentExpected) {
		sft = new SoftAssert();
		HashMap<String, Object> hm_silkroute_return_line = RMSServiceHelper.getSilkrouteReturnLineEntry(returnId);
		String silkroute_store_return_line_id = "" + hm_silkroute_return_line.get("store_return_line_id");
		String silkroute_store_return_line_reason = "" + hm_silkroute_return_line.get("reason");
		String silkroute_store_return_line_comments = "" + hm_silkroute_return_line.get("comment");

		sft.assertEquals(returnId, silkroute_store_return_line_id, "silkroute return line store_line_id ");
		sft.assertEquals(returnReasonExpected, silkroute_store_return_line_reason, "silkroute return line reason ");
		sft.assertEquals(returnCommentExpected, silkroute_store_return_line_comments,
				"silkroute return line comments ");
		sft.assertAll();

	}

	public void validateSilkrouteRTOEntry(String returnId, String rtoReasonExpected, String rtoCommentExpected,
			String rtoStatusExpected) {
		sft = new SoftAssert();
		HashMap<String, Object> hm_silkroute_rto = RMSServiceHelper.getSilkrouteRTOEntry(returnId);
		String rto_store_return_id = "" + hm_silkroute_rto.get("store_return_id");
		String rto_reason = "" + hm_silkroute_rto.get("reason");
		String rto_comment = "" + hm_silkroute_rto.get("comment");
		String rto_status = "" + hm_silkroute_rto.get("status");

		sft.assertEquals(returnId, rto_store_return_id, "checking rto entry in silkroute rto table");
		sft.assertEquals(rtoReasonExpected, rto_reason, "checking rto_reason entry in silkroute rto table");
		sft.assertEquals(rtoCommentExpected, rto_comment, "checking rto_comment entry in silkroute rto table");
		sft.assertEquals(rtoStatusExpected, rto_status, "checking rto_comment entry in silkroute rto table");
		sft.assertAll();
	}

	public void validateRMSReturnEntry(String returnId, String expectedStatusCode, String expectedComment) {
		sft = new SoftAssert();
		HashMap<String, Object> hm_rms_return = RMSServiceHelper.getRmsReturnEntry(returnId);
		String rms_store_return_id = "" + hm_rms_return.get("store_return_id");
		String rms_return_id = "" + hm_rms_return.get("id");
		String rms_return_status_code = "" + hm_rms_return.get("status_code");
		String rms_return_comments = "" + hm_rms_return.get("return_comment");

		sft.assertEquals(returnId, rms_store_return_id, "rms return  id");
		sft.assertEquals(expectedStatusCode, rms_return_status_code, "rms return  status");
		sft.assertEquals(expectedComment, rms_return_comments, "rms return  comment");
		sft.assertAll();

	}

	public void validateRMSReturnLineEntry(String rmsReturnId, String returnLineStatusCode,
			String returnLineReasonCode) {
		sft = new SoftAssert();
		String rms_return_id = RMSServiceHelper.getRmsReturnEntry(rmsReturnId).get("id").toString();
		HashMap<String, Object> hm_rms_line = RMSServiceHelper.getRmsReturnLineEntry(rms_return_id);
		String rms_return_line_reason_code = "" + hm_rms_line.get("return_reason_id");
		String rms_return_line_status_code = "" + hm_rms_line.get("status_code");

		//sft.assertEquals(returnLineStatusCode, rms_return_line_status_code, "rms return line status");
		sft.assertEquals(returnLineReasonCode, rms_return_line_reason_code, "rms return line comment");
		sft.assertAll();
	}

	public void validateOMSOrderReleaseEntry(String storeOrderId, String expectedStatus)
			throws UnsupportedEncodingException, JAXBException {
		sft = new SoftAssert();
		OrderEntry orderEntry = omsServiceHelper.getOrderEntryByStoreOrderID(storeOrderId);
		sft.assertEquals(orderEntry.getOrderReleases().iterator().next().getStatus(), expectedStatus,
				"OMS Status should change to " + expectedStatus + " in OMS ");
		sft.assertAll();
	}

}

