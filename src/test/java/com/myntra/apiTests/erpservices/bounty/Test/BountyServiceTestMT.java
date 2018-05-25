package com.myntra.apiTests.erpservices.bounty.Test;

import argo.saj.InvalidSyntaxException;

import com.myntra.absolut.cart.client.response.entry.CartEntry;
import com.myntra.apiTests.end2end.End2EndHelper;
import com.myntra.apiTests.erpservices.CustomExceptions.SCMExceptions;
import com.myntra.apiTests.erpservices.atp.ATPServiceHelper;
import com.myntra.apiTests.erpservices.bounty.BountyServiceHelper;
import com.myntra.apiTests.erpservices.bounty.BountyServiceHelperMT;
import com.myntra.apiTests.erpservices.bounty.dp.BountyServiceDP;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.erpservices.sellerapis.SellerConfig;
import com.myntra.apiTests.erpservices.ims.IMSServiceHelper;
import com.myntra.apiTests.portalservices.checkoutservice.CheckoutTestsHelper;
import com.myntra.apiTests.portalservices.ideaapi.IdeaApiHelper;
import com.myntra.apiTests.portalservices.checkoutservice.CheckoutDataProviderEnum;
import com.myntra.bounty.client.response.OrderCreationResponse;
import com.myntra.commons.codes.StatusResponse;
import com.myntra.commons.codes.StatusResponse.Type;
import com.myntra.commons.exception.ManagerException;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.oms.client.entry.OrderEntry;
import com.myntra.oms.client.entry.OrderReleaseEntry;
import com.myntra.oms.client.response.OrderResponse;
import com.myntra.oms.common.enums.PaymentStatus;
import com.myntra.test.commons.testbase.BaseTest;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jettison.json.JSONException;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.xml.bind.JAXBException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author santwana.samantray Modified By @author Abhijit.Pati for PPS
 *         testcases.
 */
public class BountyServiceTestMT extends BaseTest {

	private static Logger log = Logger.getLogger(BountyServiceTestMT.class);
	// private String login = "end2endautomation1@gmail.com";
	String password = BountyTestcasesConstants.BountyServiceTest.PASSWORD;
	BountyServiceHelperMT bountyServiceHelperMT = new BountyServiceHelperMT();
	ATPServiceHelper atpServiceHelper = new ATPServiceHelper();
	End2EndHelper end2EndHelper2 = new End2EndHelper();
	OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
	CheckoutTestsHelper checkoutTestsHelper = new CheckoutTestsHelper();
	End2EndHelper end2EndHelper = new End2EndHelper();
	IdeaApiHelper ideaApiHelper = new IdeaApiHelper();
	IMSServiceHelper imsServiceHelper = new IMSServiceHelper();
	private String login = BountyTestcasesConstants.BountyServiceTest.LOGIN_URL;
	private String login_notify = BountyTestcasesConstants.BountyServiceTest.LOGIN_URL_Decline;
	String password_notify = BountyTestcasesConstants.BountyServiceTest.PASSWORD_Decline;
	private String uidx;
	private String orderId;
	private List totalLines;
	private OrderEntry orderEntry;
	private List<OrderReleaseEntry> orderReleaseEntries;
	private String orderReleaseId;
	private Long sellerId;

	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws SQLException, IOException, JAXBException {
		DBUtilities.exUpdateQuery("update inventory set supply_type='JUST_IN_TIME' where sku_id='828703';",
				"myntra_atp");
		end2EndHelper.updateToolsApplicationProperties("cod.limit.range", "20-20000", "oms");
		bountyServiceHelperMT.refreshBountyApplicationPropertyCache();
		uidx = ideaApiHelper.getUIDXForLoginViaEmail("myntra", login);
		bountyServiceHelperMT.createNewCoupon(uidx, "bounty001", 20);
		sellerId = SellerConfig.getSellerID(SellerConfig.SellerName.HANDH);
	}

	@AfterClass(alwaysRun = true)
	public void afterclass() throws SQLException, IOException, JAXBException {
		end2EndHelper.updateToolsApplicationProperties("cod.limit.range", "1-200000", "oms");
		end2EndHelper.updateToolsApplicationProperties("bounty.get_default_address", "true", "oms");
		bountyServiceHelperMT.refreshBountyApplicationPropertyCache();
	}

	@BeforeMethod()
	public void beforeMethod() throws SQLException, IOException, JAXBException {
		end2EndHelper.updateToolsApplicationProperties("bounty.get_default_address", "true", "oms");
		bountyServiceHelperMT.refreshBountyApplicationPropertyCache();
	}

	@Test(groups = { "Regression", "MiniRegression", "ExhaustiveRegression",
	"PPS","Sanity" }, description = "Create Order Having Null PPSID")
	public void bountyServiceCreateOrderInvalidPPSID() throws IOException, JAXBException {
		String skuId1[] = { BountyTestcasesConstants.BountyServiceTest.bountyServiceCreateOrderInvalidPPSID_ITEM1+":2", BountyTestcasesConstants.BountyServiceTest.bountyServiceCreateOrderInvalidPPSID_ITEM2+":3" };
		HashMap<String, Object> orderEntry = bountyServiceHelperMT.getCreateOrderEntry(login, BountyTestcasesConstants.BountyServiceTest.PASSWORD, skuId1, "ADD", "",
				"200", "50", "");

		OrderResponse orderResponse = bountyServiceHelperMT.createOrder((CartEntry) orderEntry.get("cart"), null, login,
				"" + orderEntry.get("xid"), "0", "0", "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068, "responsive", "DEFAULT", "on");
		Assert.assertEquals(orderResponse.getStatus().getStatusType(), StatusResponse.Type.ERROR);
		Assert.assertEquals(orderResponse.getStatus().getStatusCode(), 8031);
		Assert.assertEquals(orderResponse.getStatus().getStatusMessage(), "PPS Id missing");
	}

	@Test(groups = { "Regression", "MiniRegression", "ExhaustiveRegression",
	"PPS" }, description = "Create Order while CartEntry is empty")
	public void bountyServiceCreateOrderInvalidCart() throws IOException, JAXBException {
		String skuId1[] = { BountyTestcasesConstants.BountyServiceTest.bountyServiceCreateOrderInvalidCart_ITEM1+":2", BountyTestcasesConstants.BountyServiceTest.bountyServiceCreateOrderInvalidCart_ITEM2+":3" };
		HashMap<String, Object> orderEntry = bountyServiceHelperMT.getCreateOrderEntry(login, BountyTestcasesConstants.BountyServiceTest.PASSWORD, skuId1, "ADD", "",
				"200", "50", "");

		OrderResponse orderResponse = bountyServiceHelperMT.createOrder(null, BountyTestcasesConstants.BountyServiceTest.PASSWORD, login, "" + orderEntry.get("xid"),
				"0", "0", "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068, "responsive", "DEFAULT", "on");
		Assert.assertEquals(orderResponse.getStatus().getStatusType(), StatusResponse.Type.ERROR);
		Assert.assertEquals(orderResponse.getStatus().getStatusCode(), 8007);
		Assert.assertEquals(orderResponse.getStatus().getStatusMessage(), "Empty cart. Not creating order");
	}

	@Test(groups = { "Regression", "MiniRegression", "ExhaustiveRegression",
	"PPS" }, description = "Create Order while CartEntry is empty")
	public void bountyServiceCreateOrderBlankSession() throws IOException, JAXBException {
		String skuId1[] = { BountyTestcasesConstants.BountyServiceTest.bountyServiceCreateOrderBlankSession_ITEM1+":1" };
		HashMap<String, Object> orderEntry = bountyServiceHelperMT.getCreateOrderEntry(login, BountyTestcasesConstants.BountyServiceTest.PASSWORD, skuId1, "ADD", "",
				"200", "50", "");

		OrderResponse orderResponse = bountyServiceHelperMT.createOrder((CartEntry) orderEntry.get("cart"), BountyTestcasesConstants.BountyServiceTest.PASSWORD,
				login, null, "0", "0", "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068, "responsive", "DEFAULT", "on");
		Assert.assertEquals(orderResponse.getStatus().getStatusType(), StatusResponse.Type.ERROR);
		Assert.assertEquals(orderResponse.getStatus().getStatusCode(), 8022);
		Assert.assertEquals(orderResponse.getStatus().getStatusMessage(), "Invalid session");
	}

	@Test(groups = { "Regression", "MiniRegression", "ExhaustiveRegression",
	"PPS" }, description = "Bounty Create Order")
	public void bountyServiceCreateOrder() throws IOException, JAXBException {
		String skuId1[] = { BountyTestcasesConstants.BountyServiceTest.bountyServiceCreateOrder_ITEM1+":2", BountyTestcasesConstants.BountyServiceTest.bountyServiceCreateOrder_ITEM2+":2" };

		HashMap<String, Object> orderEntry = bountyServiceHelperMT.getCreateOrderEntry(login, BountyTestcasesConstants.BountyServiceTest.PASSWORD, skuId1, "ADD", "",
				"200", "50", "");
		OrderResponse orderResponse = bountyServiceHelperMT.createOrder((CartEntry) orderEntry.get("cart"), "12345",
				login, "" + orderEntry.get("xid"), "0", "0", "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068, "responsive", "DEFAULT", "on");
		Assert.assertEquals(orderResponse.getStatus().getStatusType(), StatusResponse.Type.SUCCESS);
		Assert.assertEquals(orderResponse.getStatus().getStatusCode(), 1001);
		Assert.assertEquals(orderResponse.getStatus().getStatusMessage(), "Order added successfully");
		//Validate Order Number in xcart_orders
		String orderNumber = orderResponse.getData().get(0).getStoreOrderId();
		log.info(orderResponse.getData().get(0).getId());
		HashMap<String, Object> orderEntry2 = bountyServiceHelperMT.getxCartOrderTableDBEntry(orderNumber);
		log.info(orderEntry2.get("order_number"));
		Assert.assertNotNull(orderEntry2.get("order_number"),"Order number is not created in Bounty");
		//Validate order number in OMS
		OrderEntry orderEntry1 = omsServiceHelper.getOrderEntryByStoreOrderID(orderNumber);
		Assert.assertNotNull(orderEntry1.getStoreOrderId(),"Order Not creted in OMS");
	}

	@Test(groups = { "Regression", "MiniRegression", "ExhaustiveRegression",
	"PPS" }, description = "Bounty Create Order for OOS SKU")
	public void bountyServiceCreateOrderForOOSSKUs() throws IOException, JAXBException {
		String skuId1[] = { BountyTestcasesConstants.BountyServiceTest.bountyServiceCreateOrderForOOSSKUs_ITEM1+":2" };
		atpServiceHelper.updateInventoryDetailsForSeller(new String[] { BountyTestcasesConstants.BountyServiceTest.bountyServiceCreateOrderForOOSSKUs_ITEM1+":28:0:0:"+sellerId+":1" },"ON_HAND");
		HashMap<String, Object> orderEntry = bountyServiceHelperMT.getCreateOrderEntry(login, BountyTestcasesConstants.BountyServiceTest.PASSWORD, skuId1, "ADD", "",
				"200", "50", "");
		OrderResponse orderResponse = bountyServiceHelperMT.createOrder((CartEntry) orderEntry.get("cart"), "12345",
				login, "" + orderEntry.get("xid"), "0", "0", "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068, "responsive", "DEFAULT", "on");
		Assert.assertEquals(orderResponse.getStatus().getStatusType(), StatusResponse.Type.ERROR);
		Assert.assertEquals(orderResponse.getStatus().getStatusMessage(), "SKU id "+BountyTestcasesConstants.BountyServiceTest.bountyServiceCreateOrderForOOSSKUs_ITEM1+" in the order is OOS.");
	}

	@Test(groups = { "Regression", "MiniRegression", "ExhaustiveRegression",
	"PPS" }, description = "Bounty Queue Order")
	public void bountyQueueOrderSuccess() throws IOException, JAXBException {
		String skuId1[] = { BountyTestcasesConstants.BountyServiceTest.bountyQueueOrderSuccess_ITEM1+":2" };
		
		atpServiceHelper.updateInventoryDetailsForSeller(new String[] {BountyTestcasesConstants.BountyServiceTest.bountyQueueOrderSuccess_ITEM1+":28:10000:0:"+sellerId+":1"},"ON_HAND");
		imsServiceHelper.updateInventoryForSeller(new String[] {BountyTestcasesConstants.BountyServiceTest.bountyQueueOrderSuccess_ITEM1+":28:10000:0:"+sellerId},"ON_HAND");
		HashMap<String, Object> orderEntry = bountyServiceHelperMT.getCreateOrderEntry(login_notify,password_notify, skuId1, "ADD", "",
				"200", "50", "");
		OrderResponse orderResponse = bountyServiceHelperMT.createOrder((CartEntry) orderEntry.get("cart"), "12345",
				login, "" + orderEntry.get("xid"), "0", "0", "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068, "responsive", "DEFAULT", "on");
		OrderCreationResponse orderQueueResponse = bountyServiceHelperMT.queueOrder(
				orderResponse.getData().get(0).getStoreOrderId(), "" + orderEntry.get("xid"), login, "DEFAULT", "SUCCESS", false);
		Assert.assertEquals(orderQueueResponse.getStatus().getStatusType(), StatusResponse.Type.SUCCESS);
		Assert.assertEquals(orderQueueResponse.getStatus().getStatusCode(), 1001);
		Assert.assertEquals(orderQueueResponse.getStatus().getStatusMessage(), "Order added successfully");
	}

	@Test(groups = { "Regression", "MiniRegression", "ExhaustiveRegression",
	"PPS" }, dataProviderClass = BountyServiceDP.class, dataProvider = "queueAlreadyQueuedOrderDP", description = "Queue an Already Queued Order")
	public void queueAnAlreadyQueuedOrder(String storeOrderId, String sessionID, String login, String cartContext,
			String paymentStatus, boolean isOnHold) throws IOException, JAXBException {
		OrderCreationResponse orderQueueResponse = bountyServiceHelperMT.queueOrder(storeOrderId, sessionID, login,
				cartContext, paymentStatus, isOnHold);
		Assert.assertEquals(orderQueueResponse.getStatus().getStatusType(), StatusResponse.Type.ERROR);
		Assert.assertEquals(orderQueueResponse.getStatus().getStatusCode(), 8034);
		Assert.assertEquals(orderQueueResponse.getStatus().getStatusMessage(),
				"Order number " + storeOrderId + " is already queued");
	}

	@Test(groups = { "Regression", "MiniRegression", "ExhaustiveRegression",
	"PPS" }, description = "Queue Order When Inventory is not available")
	public void queueAnOrderWhenInventoryIsNotAvailable() throws IOException, JAXBException {
		String skuId1[] = { BountyTestcasesConstants.BountyServiceTest.queueAnOrderWhenInventoryIsNotAvailable_ITEM1+":2" };
		atpServiceHelper.updateInventoryDetailsForSeller(new String[] { BountyTestcasesConstants.BountyServiceTest.queueAnOrderWhenInventoryIsNotAvailable_ITEM1+":28:1000:0:"+sellerId+":1" },"JUST_IN_TIME");

		HashMap<String, Object> orderEntry1 = bountyServiceHelperMT.getCreateOrderEntry(login, BountyTestcasesConstants.BountyServiceTest.PASSWORD, skuId1, "ADD",
				"", "200", "50", "");
		OrderResponse orderResponse1 = bountyServiceHelperMT.createOrder((CartEntry) orderEntry1.get("cart"), "12345",
				login, "" + orderEntry1.get("xid"), "0", "0", "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068, "responsive", "DEFAULT", "on");
		atpServiceHelper.updateInventoryDetailsForSeller(new String[] { BountyTestcasesConstants.BountyServiceTest.queueAnOrderWhenInventoryIsNotAvailable_ITEM1+":28:0:0:"+sellerId+":1" },"JUST_IN_TIME");
		OrderCreationResponse orderQueueResponse = bountyServiceHelperMT.queueOrder(
				orderResponse1.getData().get(0).getStoreOrderId(), "" + orderEntry1.get("xid"), login, "DEFAULT", "SUCCESS",
				false);

		Assert.assertEquals(orderQueueResponse.getStatus().getStatusType(), StatusResponse.Type.ERROR);
		Assert.assertEquals(orderQueueResponse.getStatus().getStatusCode(), 1119);
		Assert.assertEquals(orderQueueResponse.getStatus().getStatusMessage(),
				"Failed to block 2-quantity for skuId:"+BountyTestcasesConstants.BountyServiceTest.queueAnOrderWhenInventoryIsNotAvailable_ITEM1
				+", storeId: 1, sellerId:"+sellerId+" and supplyTypeJUST_IN_TIME");
	}

	@Test(groups = { "Regression", "MiniRegression", "ExhaustiveRegression", "PPS" }, description = "Confirm Order")
	public void confirmOrderSuccess() throws IOException, JAXBException {
		String skuId1[] = { BountyTestcasesConstants.BountyServiceTest.confirmOrderSuccess_ITEM1+":2" };

		HashMap<String, Object> orderEntry1 = bountyServiceHelperMT.getCreateOrderEntry(login_notify,password_notify, skuId1, "ADD",
				"", "200", "50", "");
		OrderResponse orderResponse1 = bountyServiceHelperMT.createOrder((CartEntry) orderEntry1.get("cart"), "12345",
				login, "" + orderEntry1.get("xid"), "0", "0", "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068, "responsive", "DEFAULT", "on");
		String orderID = orderResponse1.getData().get(0).getStoreOrderId();
		OrderCreationResponse orderQueueResponse = bountyServiceHelperMT.queueOrder(orderID, "" + orderEntry1.get("xid"),
				login, "DEFAULT", "SUCCESS", true);
		Assert.assertEquals(orderQueueResponse.getStatus().getStatusType(), StatusResponse.Type.SUCCESS);

		OrderCreationResponse orderConfirmationResponse = bountyServiceHelperMT.confirmOnHoldOrder(orderID, "SUCCESS",
				BountyTestcasesConstants.BountyServiceTest.PASSWORD, false);
		Assert.assertEquals(orderConfirmationResponse.getStatus().getStatusType(), StatusResponse.Type.SUCCESS);
		Assert.assertEquals(orderConfirmationResponse.getStatus().getStatusCode(), 1004);
		Assert.assertEquals(orderConfirmationResponse.getStatus().getStatusMessage(), "Order updated successfully");
		Assert.assertEquals(orderConfirmationResponse.getStatus().getTotalCount(), 1);
	}

	@Test(groups = { "Regression", "MiniRegression", "ExhaustiveRegression",
	"PPS" }, dataProvider = "confirmOrderFailedDP", dataProviderClass = BountyServiceDP.class, description = "Confirm an already Confirmed Order")
	public void confirmAnAlreadyConfirmedOrder(String storeOrderID, String paymentStatus, String ppsID, boolean status)
			throws IOException, JAXBException {

		OrderCreationResponse orderConfirmationResponse = bountyServiceHelperMT.confirmOnHoldOrder(storeOrderID, paymentStatus,
				ppsID, status);
		Assert.assertEquals(orderConfirmationResponse.getStatus().getStatusType(), StatusResponse.Type.ERROR);
		Assert.assertEquals(orderConfirmationResponse.getStatus().getStatusCode(), 8039);
		Assert.assertEquals(orderConfirmationResponse.getStatus().getStatusMessage(),
				"Order number " + storeOrderID + " on hold already resolved");
	}

	@Test(groups = { "Regression", "MiniRegression", "ExhaustiveRegression",
	"PPS" }, dataProvider = "declineOrderSuccessDP", dataProviderClass = BountyServiceDP.class, description = "Decline Order")
	public void declineOrderSuccess(String storeOrderID, String login, String sessionID, String cartContext,
			String paymentStatus, long declineReasonID, boolean putOnHold) throws IOException, JAXBException {
		OrderCreationResponse orderDeclineResponse = bountyServiceHelperMT.declineOrder(storeOrderID, login, sessionID,
				cartContext, paymentStatus, declineReasonID, putOnHold);
		Assert.assertEquals(orderDeclineResponse.getStatus().getStatusType(), StatusResponse.Type.SUCCESS);
		Assert.assertEquals(orderDeclineResponse.getStatus().getStatusCode(), 1004);
		Assert.assertEquals(orderDeclineResponse.getStatus().getStatusMessage(), "Order updated successfully");

	}

	@Test(groups = { "Regression", "MiniRegression", "ExhaustiveRegression",
	"PPS" }, description = "OrderQueueOnHold5 From PP Status")
	public void OrderQueueOnHold5FromPPStatus() throws IOException, JAXBException {
		String skuId1[] = { BountyTestcasesConstants.BountyServiceTest.OrderQueueOnHold5FromPPStatus_ITEM1+":2" };
		HashMap<String, Object> orderEntry1 = bountyServiceHelperMT.getCreateOrderEntry(login, BountyTestcasesConstants.BountyServiceTest.PASSWORD, skuId1, "ADD",
				"", "200", "50", "");
		OrderResponse orderResponse1 = bountyServiceHelperMT.createOrder((CartEntry) orderEntry1.get("cart"), "12345",
				login, "" + orderEntry1.get("xid"), "0", "0", "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068, "responsive", "DEFAULT", "on");
		String storeOrderId = orderResponse1.getData().get(0).getStoreOrderId();
		Assert.assertEquals(bountyServiceHelperMT.getxCartOrderTableDBEntry(storeOrderId).get("status"), "PP");
		OrderCreationResponse orderQueueResponse = bountyServiceHelperMT.putOrderInHold(storeOrderId, "FAILURE");
		Assert.assertEquals(orderQueueResponse.getStatus().getStatusType(), StatusResponse.Type.SUCCESS);
		Assert.assertEquals(bountyServiceHelperMT.getxCartOrderTableDBEntry(storeOrderId).get("on_hold_reason_id_fk"), 5);

	}

	@Test(groups = { "Regression", "MiniRegression", "ExhaustiveRegression",
	"PPS" }, dataProvider = "declineOrderSuccessFromPPStatusDP", dataProviderClass = BountyServiceDP.class, description = "Decline Order When Order is in PP Status")
	public void declineOrderSuccessFromPPStatus(String storeOrderID, String login, String sessionID, String cartContext,
			String paymentStatus, long declineReasonID, boolean putOnHold) throws IOException, JAXBException, ManagerException {

		Assert.assertEquals(bountyServiceHelperMT.getxCartOrderTableDBEntry(storeOrderID).get("status"), "PP");
		OrderCreationResponse orderDeclineResponse = bountyServiceHelperMT.declineOrder(storeOrderID, login, sessionID,
				cartContext, paymentStatus, declineReasonID, putOnHold);
		Assert.assertEquals(orderDeclineResponse.getStatus().getStatusType(), StatusResponse.Type.SUCCESS);
		Assert.assertEquals(orderDeclineResponse.getStatus().getStatusCode(), 1004);
		Assert.assertEquals(orderDeclineResponse.getStatus().getStatusMessage(), "Order updated successfully");
		Assert.assertEquals(bountyServiceHelperMT.getxCartOrderTableDBEntry(storeOrderID).get("status"), "D");
		String orderID = omsServiceHelper.getOrderEntryByStoreOrderID(storeOrderID).getId().toString();
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"D");
	}

	@Test(groups = { "Regression", "MiniRegression", "ExhaustiveRegression",
	"PPS" }, dataProvider = "declineOrderSuccessFromPVStatusDP", dataProviderClass = BountyServiceDP.class, description = "Decline Order When Order is in PV Status")
	public void declineOrderSuccessFromPVStatus(String storeOrderID, String login, String sessionID, String cartContext,
			String paymentStatus, long declineReasonID, boolean putOnHold) throws IOException, JAXBException, ManagerException {

		Assert.assertEquals(bountyServiceHelperMT.getxCartOrderTableDBEntry(storeOrderID).get("status"), "PV");
		OrderCreationResponse orderDeclineResponse = bountyServiceHelperMT.declineOrder(storeOrderID, login, sessionID,
				cartContext, paymentStatus, declineReasonID, putOnHold);
		Assert.assertEquals(orderDeclineResponse.getStatus().getStatusType(), StatusResponse.Type.SUCCESS);
		Assert.assertEquals(orderDeclineResponse.getStatus().getStatusCode(), 1004);
		Assert.assertEquals(orderDeclineResponse.getStatus().getStatusMessage(), "Order updated successfully");
		Assert.assertEquals(bountyServiceHelperMT.getxCartOrderTableDBEntry(storeOrderID).get("status"), "D");
		String orderID = omsServiceHelper.getOrderEntryByStoreOrderID(storeOrderID).getId().toString();
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"D");
	}

	@Test(groups = { "Regression", "MiniRegression", "ExhaustiveRegression",
	"PPS" }, dataProvider = "declineOrderSuccessFromPVToQOnHold39StatusDP", dataProviderClass = BountyServiceDP.class, description = "Decline Order When Order is in PV and Q On Hold39 Status")
	public void declineOrderSuccessFromPVToQOnHold39Status(String storeOrderId, String login, String sessionID,
			String cartContext, String paymentStatus, long declineReasonID, boolean putOnHold)
					throws IOException, JAXBException {

		Assert.assertEquals(bountyServiceHelperMT.getxCartOrderTableDBEntry(storeOrderId).get("status"), "Q");
		Assert.assertEquals(bountyServiceHelperMT.getxCartOrderTableDBEntry(storeOrderId).get("on_hold_reason_id_fk"), 39);
		OrderCreationResponse orderDeclineResponse = bountyServiceHelperMT.declineOrder(storeOrderId, login, sessionID,
				cartContext, paymentStatus, declineReasonID, putOnHold);
		Assert.assertEquals(orderDeclineResponse.getStatus().getStatusType(), StatusResponse.Type.SUCCESS);
		Assert.assertEquals(orderDeclineResponse.getStatus().getStatusCode(), 1004);
		Assert.assertEquals(orderDeclineResponse.getStatus().getStatusMessage(), "Order updated successfully");
		Assert.assertEquals(bountyServiceHelperMT.getxCartOrderTableDBEntry(storeOrderId).get("status"), "D");
		End2EndHelper.sleep(5000L);
		orderEntry = omsServiceHelper.getOrderEntryByStoreOrderID(storeOrderId);
		orderReleaseEntries = orderEntry.getOrderReleases();
		for(OrderReleaseEntry releases:orderReleaseEntries){
			orderReleaseId = releases.getId().toString();
			Assert.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(orderReleaseId, "F", 15),
					"Order Release "+orderReleaseId+" is not in F status");
		}
	}

	@Test(groups = { "Regression", "MiniRegression", "ExhaustiveRegression",
	"PPS" }, dataProvider = "declineOrderSuccessFromPPToQOnHold39StatusDP", dataProviderClass = BountyServiceDP.class, description = "Decline Order When Order is in PP and Q On Hold39 Status")
	public void declineOrderSuccessFromPPToQOnHold39Status(String storeOrderId, String login, String sessionID,
			String cartContext, String paymentStatus, long declineReasonID, boolean putOnHold)
					throws IOException, JAXBException {

		Assert.assertEquals(bountyServiceHelperMT.getxCartOrderTableDBEntry(storeOrderId).get("status"), "Q");
		Assert.assertEquals(bountyServiceHelperMT.getxCartOrderTableDBEntry(storeOrderId).get("on_hold_reason_id_fk"), 39);
		OrderCreationResponse orderDeclineResponse = bountyServiceHelperMT.declineOrder(storeOrderId, login, sessionID,
				cartContext, paymentStatus, declineReasonID, putOnHold);
		Assert.assertEquals(orderDeclineResponse.getStatus().getStatusType(), StatusResponse.Type.SUCCESS);
		Assert.assertEquals(orderDeclineResponse.getStatus().getStatusCode(), 1004);
		Assert.assertEquals(orderDeclineResponse.getStatus().getStatusMessage(), "Order updated successfully");
		Assert.assertEquals(bountyServiceHelperMT.getxCartOrderTableDBEntry(storeOrderId).get("status"), "D");
		End2EndHelper.sleep(5000L);
		orderEntry = omsServiceHelper.getOrderEntryByStoreOrderID(storeOrderId);
		orderReleaseEntries = orderEntry.getOrderReleases();
		for(OrderReleaseEntry releases:orderReleaseEntries){
			orderReleaseId = releases.getId().toString();
			Assert.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(orderReleaseId, "F", 15),
					"Order Release "+orderReleaseId+" is not in F status");
		}
	}

	@Test(groups = { "Regression", "MiniRegression", "ExhaustiveRegression",
	"PPS" }, dataProvider = "declineBountyQueueOrderSuccessDP", dataProviderClass = BountyServiceDP.class, description = "Decline Bounty Queue Order")
	public void declineBountyQueueOrderSuccess(String storeOrderId, String login, String sessionID, String cartContext,
			String paymentStatus, long declineReasonID, boolean putOnHold) throws IOException, JAXBException {
		OrderCreationResponse orderDeclineResponse = bountyServiceHelperMT.declineOrder(storeOrderId, login, sessionID,
				cartContext, paymentStatus, declineReasonID, putOnHold);
		Assert.assertEquals(orderDeclineResponse.getStatus().getStatusType(), StatusResponse.Type.ERROR);
		Assert.assertEquals(orderDeclineResponse.getStatus().getStatusCode(), 8038);
		Assert.assertEquals(orderDeclineResponse.getStatus().getStatusMessage(),
				"Order processing has started for the order number " + storeOrderId);

	}

	@Test(groups = { "Regression", "MiniRegression", "ExhaustiveRegression",
	"PPS" }, description = "OrderQueueOnHold5 From PP Status")
	public void OrderQueueOnHold5FromPVStatus() throws IOException, JAXBException {
		String skuId1[] = { BountyTestcasesConstants.BountyServiceTest.OrderQueueOnHold5FromPVStatus_ITEM1+":2" };
		HashMap<String, Object> orderEntry1 = bountyServiceHelperMT.getCreateOrderEntry(login, BountyTestcasesConstants.BountyServiceTest.PASSWORD, skuId1, "ADD",
				"", "200", "50", "");
		OrderResponse orderResponse1 = bountyServiceHelperMT.createOrder((CartEntry) orderEntry1.get("cart"), "12345",
				login, "" + orderEntry1.get("xid"), "0", "0", "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068, "responsive", "DEFAULT", "cod");
		String storeOrderId = orderResponse1.getData().get(0).getStoreOrderId();
		Assert.assertEquals(bountyServiceHelperMT.getxCartOrderTableDBEntry(storeOrderId).get("status"), "PV");
		OrderCreationResponse orderQueueResponse = bountyServiceHelperMT.putOrderInHold(storeOrderId, "SUCCESS");
		Assert.assertEquals(orderQueueResponse.getStatus().getStatusType(), StatusResponse.Type.ERROR);
	}

	@Test(groups = { "Regression", "MiniRegression", "ExhaustiveRegression",
	"PPS" }, dataProvider = "declineAlreadyDeclinedOrderDP", dataProviderClass = BountyServiceDP.class, description = "Decline already Declined Order")
	public void declineAlreadyDeclinedOrder(String storeOrderId, String login, String sessionID, String cartContext,
			String paymentStatus, long declineReasonID, boolean putOnHold) throws IOException, JAXBException {
		OrderCreationResponse orderDeclineResponse = bountyServiceHelperMT.declineOrder(storeOrderId, login, sessionID,
				cartContext, paymentStatus, declineReasonID, putOnHold);
		Assert.assertEquals(orderDeclineResponse.getStatus().getStatusType(), StatusResponse.Type.ERROR);
		Assert.assertEquals(orderDeclineResponse.getStatus().getStatusCode(), 8035);
		Assert.assertEquals(orderDeclineResponse.getStatus().getStatusMessage(),
				"Order number " + storeOrderId + " is already declined");
	}

	@Test(groups = { "Regression", "MiniRegression", "ExhaustiveRegression",
	"PPS" }, enabled = true, priority = 5, description = "Decline Order Should Free Up ATP Inventory")
	public void declineOrderShouldFreeUpBountyInventory() throws IOException, JAXBException, SQLException {
		String skuId1[] = { BountyTestcasesConstants.BountyServiceTest.declineOrderShouldFreeUpBountyInventory_ITEM1+":2" };

		HashMap<String, Object> atpInventoryDetailsBeforePlacingOrder = atpServiceHelper
				.getAtpInvDetails(new String[] { BountyTestcasesConstants.BountyServiceTest.declineOrderShouldFreeUpBountyInventory_ITEM1 }).get(BountyTestcasesConstants.BountyServiceTest.declineOrderShouldFreeUpBountyInventory_ITEM1);
		HashMap<String, Object> orderEntry1 = bountyServiceHelperMT.getCreateOrderEntry(login, BountyTestcasesConstants.BountyServiceTest.PASSWORD, skuId1, "ADD",
				"", "200", "50", "");
		OrderResponse orderResponse1 = bountyServiceHelperMT.createOrder((CartEntry) orderEntry1.get("cart"), "12345",
				login, "" + orderEntry1.get("xid"), "0", "0", "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068, "responsive", "DEFAULT", "on");
		String storeOrderId = orderResponse1.getData().get(0).getStoreOrderId();
		OrderCreationResponse orderQueueResponse = bountyServiceHelperMT.queueOrder(storeOrderId, "" + orderEntry1.get("xid"),
				login, "DEFAULT", "SUCCESS", true);
		Assert.assertEquals(orderQueueResponse.getStatus().getStatusType(), StatusResponse.Type.SUCCESS);
		End2EndHelper.sleep(20000);
		HashMap<String, Object> atpInventoryAfterQueuingOrder = atpServiceHelper
				.getAtpInvDetails(new String[] { BountyTestcasesConstants.BountyServiceTest.declineOrderShouldFreeUpBountyInventory_ITEM1 }).get(BountyTestcasesConstants.BountyServiceTest.declineOrderShouldFreeUpBountyInventory_ITEM1);

		OrderCreationResponse orderDeclineResponse = bountyServiceHelperMT.declineOrder(storeOrderId, login,
				"" + orderEntry1.get("xid"), "DEFAULT", "FAILURE", 2, false);
		End2EndHelper.sleep(5000L);
		orderEntry = omsServiceHelper.getOrderEntryByStoreOrderID(storeOrderId);
		orderReleaseEntries = orderEntry.getOrderReleases();
		for(OrderReleaseEntry releases:orderReleaseEntries){
			orderReleaseId = releases.getId().toString();
			Assert.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(orderReleaseId, "F", 15),
					"Order Release "+orderReleaseId+" is not in F status");
		}
		End2EndHelper.sleep(20000);
		HashMap<String, Object> atpInventoryAfterDeclineOrder = atpServiceHelper
				.getAtpInvDetails(new String[] { BountyTestcasesConstants.BountyServiceTest.declineOrderShouldFreeUpBountyInventory_ITEM1 }).get(BountyTestcasesConstants.BountyServiceTest.declineOrderShouldFreeUpBountyInventory_ITEM1);

		Assert.assertEquals(atpInventoryAfterDeclineOrder.get("blocked_order_count"),
				atpInventoryDetailsBeforePlacingOrder.get("blocked_order_count"),
				"ATP Inventory Count Should Reduce After Decline Order");
		Assert.assertEquals((int) atpInventoryDetailsBeforePlacingOrder.get("blocked_order_count"),
				((int) atpInventoryAfterQueuingOrder.get("blocked_order_count")) - 2,
				"ATP Inventory Count Should Reduce After Decline Order");
	}

	@Test(enabled = true)
	public void declineOrderShouldUnBlockTheCouponUsage() throws IOException, JAXBException, SQLException, InvalidSyntaxException {
		String skuId1[] = { "796931:2" };
		IdeaApiHelper ideahelper = new IdeaApiHelper();
		BountyServiceHelper bountyHelper = new BountyServiceHelper();

		HashMap<String, Object> orderEntry1 = bountyServiceHelperMT.getCreateOrderEntryWithCoupon(login, BountyTestcasesConstants.BountyServiceTest.PASSWORD, skuId1,
				"ADD", "bounty001", false, false, false);
		OrderResponse orderResponse1 = bountyServiceHelperMT.createOrder((CartEntry) orderEntry1.get("cart"), "12345",
				ideahelper.getUIDXForLoginViaEmail("myntra", login), "" + orderEntry1.get("xid"), "0", "0", "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068,
				"responsive", "DEFAULT", "on");
		String storeOrderId = orderResponse1.getData().get(0).getStoreOrderId();
		int couponUsageAfterOrderCreation = (int) bountyHelper.getxcart_discount_couponsDBEntry("bounty001")
				.get("times_locked");

		OrderCreationResponse orderDeclineResponse = bountyServiceHelperMT.declineOrder(storeOrderId, login, "", "DEFAULT",
				"SUCCESS", 2, false);
		Assert.assertEquals(orderDeclineResponse.getStatus().getStatusType(), StatusResponse.Type.SUCCESS);

		int couponUsageAfterOrderDecline = (int) bountyHelper.getxcart_discount_couponsDBEntry("bounty001")
				.get("times_locked");
		Assert.assertEquals(couponUsageAfterOrderCreation - 1, couponUsageAfterOrderDecline,
				"coupon usage should decrease after order decline");
	}

	@Test(enabled = true)
	public void verifyCouponShouldBeLockedAfterQueueOrder() throws IOException, JAXBException, SQLException, InvalidSyntaxException {

		String skuId1[] = { BountyTestcasesConstants.BountyServiceTest.verifyCouponShouldBeLockedAfterQueueOrder_ITEM1+":2" };
		IdeaApiHelper ideahelper = new IdeaApiHelper();
		BountyServiceHelper bountyHelper = new BountyServiceHelper();

		bountyServiceHelperMT.createNewCoupon(uidx, "bounty001", 20);
		HashMap<String, Object> orderEntry1 = bountyServiceHelperMT.getCreateOrderEntryWithCoupon(login, BountyTestcasesConstants.BountyServiceTest.PASSWORD, skuId1,
				"ADD", "bounty001", false, false, false);
		int couponUsageBeforeOrderCreation = (int) bountyHelper.getxcart_discount_couponsDBEntry("bounty001")
				.get("times_locked");
		OrderResponse orderResponse1 = bountyServiceHelperMT.createOrder((CartEntry) orderEntry1.get("cart"), "12345",
				ideahelper.getUIDXForLoginViaEmail("myntra", login), "" + orderEntry1.get("xid"), "0", "0", "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068,
				"responsive", "DEFAULT", "on");
		String storeOrderId = orderResponse1.getData().get(0).getStoreOrderId();
		OrderCreationResponse orderQueueResponse = bountyServiceHelperMT.queueOrder(storeOrderId, "" + orderEntry1.get("xid"),
				login, "DEFAULT", "SUCCESS", false);
		Assert.assertEquals(orderQueueResponse.getStatus().getStatusType(), StatusResponse.Type.SUCCESS);
		int couponUsageAfterOrderQueue = (int) bountyHelper.getxcart_discount_couponsDBEntry("bounty001")
				.get("times_locked");
		String coupon = (String) bountyHelper.getxCartOrderTableDBEntry(storeOrderId).get("coupon");
		String couponDiscount = bountyHelper.getxCartOrderTableDBEntry(storeOrderId).get("coupon_discount").toString();
		Assert.assertEquals(coupon,"bounty001","coupon is not added");
		Assert.assertEquals(couponDiscount, "239.60","discount is not applied");
		//		Assert.assertEquals(couponUsageBeforeOrderCreation, couponUsageAfterOrderQueue - 1,
		//				"Coupon Usage count should increase after order is queued");

	}

	@Test(enabled = true)
	public void verifyCartShouldBeDeletedAfterSuccessfulQueuingOrder() throws IOException, JAXBException, SQLException {
		String skuId1[] = { BountyTestcasesConstants.BountyServiceTest.verifyCartShouldBeDeletedAfterSuccessfulQueuingOrder_ITEM1+":2" };
		CartEntry cartEntry = null;

		HashMap<String, Object> orderEntry1 = bountyServiceHelperMT.getCreateOrderEntry(login, BountyTestcasesConstants.BountyServiceTest.PASSWORD, skuId1, "ADD",
				"", "200", "50", "");
		OrderResponse orderResponse1 = bountyServiceHelperMT.createOrder((CartEntry) orderEntry1.get("cart"), "12345",
				login, "" + orderEntry1.get("xid"), "0", "0", "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068, "responsive", "DEFAULT", "on");
		String storeOrderId = orderResponse1.getData().get(0).getStoreOrderId();
		OrderCreationResponse orderQueueResponse = bountyServiceHelperMT.queueOrder(storeOrderId, "" + orderEntry1.get("xid"),
				login, "DEFAULT", "SUCCESS", false);
		Assert.assertEquals(orderQueueResponse.getStatus().getStatusType(), StatusResponse.Type.SUCCESS);
		Assert.assertEquals(bountyServiceHelperMT.getxCartOrderTableDBEntry(storeOrderId).get("status"), "Q");
		// Check if cart is empty after order is queue successfully
		cartEntry = CheckoutTestsHelper.getCart(login, "DEFAULT", "" + orderEntry1.get("xid"));
		End2EndHelper.sleep(10000L);//Adding time as cart delete is async process
		Assert.assertEquals(cartEntry.getCartItemEntries().size(), 0);
	}

	@Test(groups = { "Regression", "MiniRegression", "ExhaustiveRegression",
	"PPS" }, dataProvider = "confirmOrderNeverOnHoldDP", dataProviderClass = BountyServiceDP.class, description = "Confirm an order which never went on hold status")
	public void confirmOrderNeverOnHold(String storeOrderId, String paymentStatus, String ppsID, boolean status)
			throws IOException, JAXBException {

		OrderCreationResponse orderConfirmationResponse = bountyServiceHelperMT.confirmOnHoldOrder(storeOrderId, paymentStatus,
				ppsID, status);
		Assert.assertEquals(orderConfirmationResponse.getStatus().getStatusType(), StatusResponse.Type.ERROR);
		Assert.assertEquals(orderConfirmationResponse.getStatus().getStatusCode(), 8032);
		Assert.assertEquals(orderConfirmationResponse.getStatus().getStatusMessage(),
				"Order number " + storeOrderId + " never was on hold");
	}

	@Test(groups = { "Regression", "MiniRegression", "ExhaustiveRegression",
	"PPS" }, description = " Decline OrderQueueOnHold5 From PP Status")
	public void declineOrderQueueOnHold5FromPPStatus() throws IOException, JAXBException {
		String skuId1[] = { BountyTestcasesConstants.BountyServiceTest.declineOrderQueueOnHold5FromPPStatus_ITEM1+":2" };
		HashMap<String, Object> orderEntry1 = bountyServiceHelperMT.getCreateOrderEntry(login, BountyTestcasesConstants.BountyServiceTest.PASSWORD, skuId1, "ADD",
				"", "200", "50", "");
		OrderResponse orderResponse1 = bountyServiceHelperMT.createOrder((CartEntry) orderEntry1.get("cart"), "12345",
				login, "" + orderEntry1.get("xid"), "0", "0", "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068, "responsive", "DEFAULT", "on");
		String storeOrderId = orderResponse1.getData().get(0).getStoreOrderId();
		Assert.assertEquals(bountyServiceHelperMT.getxCartOrderTableDBEntry(storeOrderId).get("status"), "PP");
		OrderCreationResponse orderQueueResponse = bountyServiceHelperMT.putOrderInHold(storeOrderId, "SUCCESS");
		Assert.assertEquals(orderQueueResponse.getStatus().getStatusType(), StatusResponse.Type.SUCCESS);
		Assert.assertEquals(bountyServiceHelperMT.getxCartOrderTableDBEntry(storeOrderId).get("on_hold_reason_id_fk"), 5);

		OrderCreationResponse orderDeclineResponse = bountyServiceHelperMT.declineOrder(storeOrderId, login,
				"" + orderEntry1.get("xid"), "DEFAULT", "FAILURE", 2, false);
		Assert.assertEquals(orderDeclineResponse.getStatus().getStatusType(), StatusResponse.Type.ERROR);
		Assert.assertEquals(orderDeclineResponse.getStatus().getStatusCode(), 8010);
		// Assert.assertEquals(orderDeclineResponse.getStatus().getStatusMessage(),
		// "Order id "+orderID+" is already declined");
	}

	@Test(dataProvider = "orderCreationShouldFailIfTheIfTheOrderValueIsLessThanCODMinValue_PPSDP", dataProviderClass = BountyServiceDP.class, groups = {
			"Regression", "MiniRegression", "ExhaustiveRegression",
	"PPS" }, enabled = false, priority = 1, description = "PPS Order Creation Should Fail If The Order Value Is Less Than COD Min Value - COD")
	public void orderCreationShouldFailIfTheIfTheOrderValueIsLessThanCODMinValue_OnlyCOD(CartEntry cartEntry,
			String ppsID, String login, String sessionId, String binNumber, String pgDiscount, String emiCharge,
			String address, String clientContext, String cartContext, String paymentMethod, String statusCode,
			String expectedData) throws JsonParseException, JsonMappingException, IOException, JAXBException {

		OrderResponse orderCreationResponse = bountyServiceHelperMT.createOrder(cartEntry, ppsID, login, sessionId,
				binNumber, pgDiscount, emiCharge, address, clientContext, cartContext, paymentMethod);
		Assert.assertEquals(statusCode, String.valueOf(orderCreationResponse.getStatus().getStatusCode()),
				"Verify Status Code is same.");
	}

	@Test(dataProvider = "orderCreationShouldFailIfTheIfTheOrderValueIsLessThanCODMinValue_CBLPCODDP", dataProviderClass = BountyServiceDP.class, groups = {
			"Regression", "MiniRegression", "ExhaustiveRegression",
	"PPS" }, enabled = false, priority = 2, description = "PPS Order Creation Should Fail If The Order Value Is Less Than COD Min Value - COD+LP+CB")
	public void orderCreationShouldFailIfTheIfTheOrderValueIsLessThanCODMinValue_CBLPCOD(CartEntry cartEntry,
			String ppsID, String login, String sessionId, String binNumber, String pgDiscount, String emiCharge,
			String address, String clientContext, String cartContext, String paymentMethod, String statusCode,
			String expectedData) throws IOException, JAXBException {

		OrderResponse orderCreationResponse = bountyServiceHelperMT.createOrder(cartEntry, ppsID, login, sessionId,
				binNumber, pgDiscount, emiCharge, address, clientContext, cartContext, paymentMethod);

		Assert.assertEquals(statusCode, String.valueOf(orderCreationResponse.getStatus().getStatusCode()),
				"Verify Status Code is same.");

	}

	@Test(dataProvider = "orderCreationShouldPassIfTheIfTheOrderValueIsMoreThanCODMinValue_CBLPCODDP", dataProviderClass = BountyServiceDP.class, groups = {
			"Regression", "MiniRegression", "ExhaustiveRegression",
	"PPS" }, enabled = true, priority = 3, description = "Order Creation Should Pass If The If The Order Value Is More Than COD Min Value - CB+LP+COD")
	public void orderCreationShouldPassIfTheIfTheOrderValueIsMoreThanCODMinValue_CBLPCOD(CartEntry cartEntry,
			String ppsID, String login, String sessionId, String binNumber, String pgDiscount, String emiCharge,
			String address, String clientContext, String cartContext, String paymentMethod, String statusCode,
			String expectedData) throws JsonParseException, JsonMappingException, IOException, JAXBException {

		OrderResponse orderCreationResponse = bountyServiceHelperMT.createOrder(cartEntry, ppsID, login, sessionId,
				binNumber, pgDiscount, emiCharge, address, clientContext, cartContext, paymentMethod);
		Assert.assertEquals(statusCode, String.valueOf(orderCreationResponse.getStatus().getStatusCode()),
				"Verify Status Code is same.");
	}

	@Test(dataProvider = "orderCreationShouldFailIfTheOrderValueIsMoreThanCODMaxValue_CODDP", dataProviderClass = BountyServiceDP.class, groups = {
			"Regression", "MiniRegression", "ExhaustiveRegression",
	"PPS" }, enabled = false, priority = 4, description = "Order Creation Should Fail If The Order Value Is More Than COD Max Value COD")
	public void orderCreationShouldFailIfTheOrderValueIsMoreThanCODMaxValue_COD(CartEntry cartEntry, String ppsID,
			String login, String sessionId, String binNumber, String pgDiscount, String emiCharge, String address,
			String clientContext, String cartContext, String paymentMethod, String statusCode, String expectedData)
					throws JsonParseException, JsonMappingException, IOException, JAXBException {

		OrderResponse orderCreationResponse = bountyServiceHelperMT.createOrder(cartEntry, ppsID, login, sessionId,
				binNumber, pgDiscount, emiCharge, address, clientContext, cartContext, paymentMethod);

		Assert.assertEquals(statusCode, String.valueOf(orderCreationResponse.getStatus().getStatusCode()),
				"Verify Status Code is same.");

	}

	@Test(dataProvider = "orderCreationShouldFailIfTheOrderValueIsMoreThanCODMaxValue_CBLPCODDP", dataProviderClass = BountyServiceDP.class, groups = {
			"Regression", "MiniRegression", "ExhaustiveRegression",
	"PPS" }, enabled = true, priority = 5, description = "Order Creation Should Fail If The Order Value Is More Than COD Max Value CB+LP+CODD")
	public void orderCreationShouldFailIfTheOrderValueIsMoreThanCODMaxValue_CBLPCOD(CartEntry cartEntry, String ppsID,
			String login, String sessionId, String binNumber, String pgDiscount, String emiCharge, String address,
			String clientContext, String cartContext, String paymentMethod, String statusCode, String expectedData)
					throws JsonParseException, JsonMappingException, IOException, JAXBException {

		OrderResponse orderResponse = bountyServiceHelperMT.createOrder(cartEntry, ppsID, login, sessionId, binNumber,
				pgDiscount, emiCharge, address, clientContext, cartContext, paymentMethod);
		Assert.assertEquals(statusCode, String.valueOf(orderResponse.getStatus().getStatusCode()),
				"Verify Status Code is same.");

	}

	@Test(enabled = true, groups = { "regression", "Smoke",
	"tryandbuy" }, description = "Verify multple item item is available for TryandBuy, Tnb flag should be available at item level from Cart.")
	public void verifyMultipleItemAreAvailableTryAndBuyAndTnbFlagShouldAvailableAtItemLevelFromCart() throws Exception {
		End2EndHelper end2EndHelper = new End2EndHelper();
		String storeOrderId;
		String skuId[] = { BountyTestcasesConstants.BountyServiceTest.verifyMultipleItemAreAvailableTryAndBuyAndTnbFlagShouldAvailableAtItemLevelFromCart_ITEM1+":1", 
				BountyTestcasesConstants.BountyServiceTest.verifyMultipleItemAreAvailableTryAndBuyAndTnbFlagShouldAvailableAtItemLevelFromCart_ITEM2+":1" };

		HashMap<Long, Boolean> hm = new HashMap<Long, Boolean>();
		hm.put((long) 3836, Boolean.TRUE);
		hm.put((long) 945928, Boolean.TRUE);

		orderId = end2EndHelper.createOrder(login, password, BountyTestcasesConstants.Pincodes.PINCODE_560068, skuId, "", false, true, false, "", true);
		System.out.println("Order Created :" + orderId);
		
		omsServiceHelper.checkReleaseStatusForOrder(orderId, "WP");
		orderEntry = omsServiceHelper.getOrderEntry(orderId);
		String storeOrderID = orderEntry.getStoreOrderId();
		
		for (String skuAndQuantity : skuId) {
			String[] skuAndQuantitySplit = skuAndQuantity.split(":");
			String sku = skuAndQuantitySplit[0].trim();
			String query = "Select is_try_and_buy from xcart_order_details where orderid in (select orderid from xcart_orders where order_number='"+ storeOrderID +"') and sku_id='" + sku + "';";
			List SelectedRecodes = bountyServiceHelperMT.getxCartOrderDetailsTableDBEntry(storeOrderID, query);
			HashMap<String, Integer> txResult = (HashMap<String, Integer>) SelectedRecodes.get(0);
			System.out.println("TryAndBuy flag:" + txResult.get("is_try_and_buy"));
			for (Map.Entry<Long, Boolean> m : hm.entrySet()) {
				if (m.getKey().equals(Long.parseLong(sku))) {
					if (Boolean.TRUE == m.getValue()) {
						Assert.assertEquals(txResult.get("is_try_and_buy").intValue(), 1,
								m.getKey() + ":TryAndBuy Flag value should be same");
					} else if (Boolean.FALSE == m.getValue()) {
						Assert.assertEquals(txResult.get("is_try_and_buy").intValue(), 0,
								m.getKey() + ":TryAndBuy Flag value should be same");
					}
				}
			}
		}
	}

	@Test(enabled = true, groups = { "regression",
	"tryandbuy" }, description = "if order placed with one TnB one Non TnB Skus , TnB flag should be there in bounty table myntra_order.xcart_order_details.")
	public void verifyTheIsTryAndBuyFlagInXCartOrderDetailsTableOneIsTryAndBuyAndOneIsNonTryAndBuyForOrder()
			throws Exception {

		String orderID;
		String skuId[] = { BountyTestcasesConstants.BountyServiceTest.verifyTheIsTryAndBuyFlagInXCartOrderDetailsTableOneIsTryAndBuyAndOneIsNonTryAndBuyForOrder_ITEM1+":1",
				BountyTestcasesConstants.BountyServiceTest.verifyTheIsTryAndBuyFlagInXCartOrderDetailsTableOneIsTryAndBuyAndOneIsNonTryAndBuyForOrder_ITEM2+":1" };
		HashMap<Long, Boolean> hm = new HashMap<Long, Boolean>();
		hm.put((long) 3856, Boolean.TRUE);
		hm.put((long) 330411, Boolean.FALSE);
		
		HashMap<String, Object> orderEntry1 = bountyServiceHelperMT.getCreateOrderEntry(login, BountyTestcasesConstants.BountyServiceTest.PASSWORD, skuId, "ADD",
				"", "200", "50", "");
		CartEntry cartEntry = bountyServiceHelperMT.setTryAndBuyInCart(hm,(CartEntry) orderEntry1.get("cart"));
		OrderResponse orderResponse1 = bountyServiceHelperMT.createOrder(cartEntry, "12345",
				login, "" + orderEntry1.get("xid"), "0", "0", "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068, "responsive", "DEFAULT", "on");
		String storeOrderID = orderResponse1.getData().get(0).getStoreOrderId();
		
		OrderCreationResponse orderQueueResponse = bountyServiceHelperMT.queueOrder(storeOrderID, "" + orderEntry1.get("xid"),
				login, "DEFAULT", "SUCCESS", false);
		orderID = omsServiceHelper.getOrderId(storeOrderID);
		omsServiceHelper.checkReleaseStatusForOrder(orderID, "Q");
		End2EndHelper.sleep(5000L);

		for (String skuAndQuantity : skuId) {
			String[] skuAndQuantitySplit = skuAndQuantity.split(":");
			String sku = skuAndQuantitySplit[0].trim();
			String query = "Select is_try_and_buy from xcart_order_details where orderid in (select orderid from xcart_orders where order_number='"+ storeOrderID +"') and sku_id='" + sku + "';";
			List SelectedRecodes = bountyServiceHelperMT.getxCartOrderDetailsTableDBEntry(storeOrderID, query);
			HashMap<String, Integer> txResult = (HashMap<String, Integer>) SelectedRecodes.get(0);

			for (Map.Entry<Long, Boolean> m : hm.entrySet()) {
				if (m.getKey().equals(Long.parseLong(sku))) {
					if (Boolean.TRUE == m.getValue()) {
						Assert.assertEquals(txResult.get("is_try_and_buy").intValue(), 1,
								m.getKey() + ":TryAndBuy Flag value should be same");
					} else if (Boolean.FALSE == m.getValue()) {
						Assert.assertEquals(txResult.get("is_try_and_buy").intValue(), 0,
								m.getKey() + ":TryAndBuy Flag value should be same");
					}
				}
			}
		}
	}

	@Test(enabled = true, groups = { "regression",
	"tryandbuy" }, description = "If order placed TnB useing 2 items and 2 items in normal buy verify bounty TnB flag should be set to true on Bounty and same should be propagated to OMS.")
	public void verifyFlagShouldBePropagatedInBountyToOmsIfMultipleTnbAndNonTnbItemsPlaced() throws Exception {

		End2EndHelper end2EndHelper = new End2EndHelper();

		DBUtilities.exUpdateQuery("UPDATE wh_inventory SET inventory_count=100000,blocked_order_count=0 where sku_id in (3834,3856,330411) and warehouse_id in (1,19,36,28);", "myntra_ims");
		DBUtilities.exUpdateQuery("update inventory set `available_in_warehouses`='1,19,36,28', `inventory_count`=100000, `blocked_order_count`=0 where sku_id in (3834,3856,330411)", "myntra_atp");
		String orderID;
		String skuId[] = { BountyTestcasesConstants.BountyServiceTest.verifyFlagShouldBePropagatedInBountyToOmsIfMultipleTnbAndNonTnbItemsPlaced_ITEM1+":1", 
				BountyTestcasesConstants.BountyServiceTest.verifyFlagShouldBePropagatedInBountyToOmsIfMultipleTnbAndNonTnbItemsPlaced_ITEM2+":1",
				BountyTestcasesConstants.BountyServiceTest.verifyFlagShouldBePropagatedInBountyToOmsIfMultipleTnbAndNonTnbItemsPlaced_ITEM3+":1" };
		HashMap<Long, Boolean> hm = new HashMap<Long, Boolean>();
		hm.put((long) 3834, Boolean.TRUE);
		hm.put((long) 3856, Boolean.TRUE);
		hm.put((long) 330411, Boolean.FALSE);

		HashMap<String, Object> orderEntry1 = bountyServiceHelperMT.getCreateOrderEntry(login, BountyTestcasesConstants.BountyServiceTest.PASSWORD, skuId, "ADD",
				"", "200", "50", "");
		CartEntry cartEntry = bountyServiceHelperMT.setTryAndBuyInCart(hm,(CartEntry) orderEntry1.get("cart"));
		OrderResponse orderResponse1 = bountyServiceHelperMT.createOrder(cartEntry, "12345",
				login, "" + orderEntry1.get("xid"), "0", "0", "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068, "responsive", "DEFAULT", "on");
		String storeOrderID = orderResponse1.getData().get(0).getStoreOrderId();
		
		// Bounty verification
		for (String skuAndQuantity : skuId) {
			String[] skuAndQuantitySplit = skuAndQuantity.split(":");
			String sku = skuAndQuantitySplit[0].trim();
			String query = "Select is_try_and_buy from xcart_order_details where orderid in (select orderid from xcart_orders where order_number='"+ storeOrderID +"') and sku_id='" + sku + "';";
			List SelectedRecodes = bountyServiceHelperMT.getxCartOrderDetailsTableDBEntry(storeOrderID, query);
			HashMap<String, Integer> txResult = (HashMap<String, Integer>) SelectedRecodes.get(0);
			for (Map.Entry<Long, Boolean> m : hm.entrySet()) {
				if (m.getKey().equals(Long.parseLong(sku))) {
					if (Boolean.TRUE == m.getValue()) {
						Assert.assertEquals(txResult.get("is_try_and_buy").intValue(), 1,
								m.getKey() + ":TryAndBuy Flag value should be same");
					} else if (Boolean.FALSE == m.getValue()) {
						Assert.assertEquals(txResult.get("is_try_and_buy").intValue(), 0,
								m.getKey() + ":TryAndBuy Flag value should be same");
					}
				}
			}
		}
	}

	@Test(enabled = true, groups = { "regression",
	"tryandbuy" }, description = "Verify flag should be propagated in bounty to oms if multiple items placed")
	public void verifyFlagShouldBePropagatedInBountyToOmsIfMultipleItemsPlaced() throws Exception {

		End2EndHelper end2EndHelper = new End2EndHelper();
		OMSServiceHelper omsServiceHelper = new OMSServiceHelper();

		String orderID;
		String skuId[] = { BountyTestcasesConstants.BountyServiceTest.verifyFlagShouldBePropagatedInBountyToOmsIfMultipleItemsPlaced_ITEM1+":1",
				BountyTestcasesConstants.BountyServiceTest.verifyFlagShouldBePropagatedInBountyToOmsIfMultipleItemsPlaced_ITEM2+":1" };
		HashMap<Long, Boolean> hm = new HashMap<Long, Boolean>();
		hm.put((long) 3836, Boolean.TRUE);
		hm.put((long) 3856, Boolean.TRUE);

		HashMap<String, Object> orderEntry1 = bountyServiceHelperMT.getCreateOrderEntry(login, BountyTestcasesConstants.BountyServiceTest.PASSWORD, skuId, "ADD",
				"", "200", "50", "");
		CartEntry cartEntry = bountyServiceHelperMT.setTryAndBuyInCart(hm,(CartEntry) orderEntry1.get("cart"));
		OrderResponse orderResponse1 = bountyServiceHelperMT.createOrder(cartEntry, "12345",
				login, "" + orderEntry1.get("xid"), "0", "0", "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068, "responsive", "DEFAULT", "on");
		String storeOrderID = orderResponse1.getData().get(0).getStoreOrderId();

		// Bounty verification
		for (String skuAndQuantity : skuId) {
			String[] skuAndQuantitySplit = skuAndQuantity.split(":");
			String sku = skuAndQuantitySplit[0].trim();
			String query = "Select is_try_and_buy from xcart_order_details where orderid in (select orderid from xcart_orders where order_number='"+ storeOrderID +"') and sku_id='" + sku + "';";
			List SelectedRecodes = bountyServiceHelperMT.getxCartOrderDetailsTableDBEntry(storeOrderID, query);

			HashMap<String, Integer> txResult = (HashMap<String, Integer>) SelectedRecodes.get(0);
			for (Map.Entry<Long, Boolean> m : hm.entrySet()) {
				if (m.getKey().equals(Long.parseLong(sku))) {
					if (Boolean.TRUE == m.getValue()) {
						Assert.assertEquals(txResult.get("is_try_and_buy").intValue(), 1,
								m.getKey() + ":TryAndBuy Flag value should be same");
					} else if (Boolean.FALSE == m.getValue()) {
						Assert.assertEquals(txResult.get("is_try_and_buy").intValue(), 0,
								m.getKey() + ":TryAndBuy Flag value should be same");
					}
				}
			}
		}
	}

	@Test(enabled = true, groups = { "regression", "Smoke",
	"tryandbuy" }, description = "Verify single item is available for TryAndBuy, Tnb flag should be available at item level from Cart.")
	public void verifySingleItemIsAvailableTryAndBuyAndTnbFlagShouldAvailableAtItemLevelFromCart() throws Exception {

		String orderID;
		String skuId[] = { BountyTestcasesConstants.BountyServiceTest.verifySingleItemIsAvailableTryAndBuyAndTnbFlagShouldAvailableAtItemLevelFromCart_ITEM1+":2" };
		HashMap<Long, Boolean> hm = new HashMap<Long, Boolean>();
		hm.put((long) 3832, Boolean.TRUE);
		RequestGenerator requestGenerator = checkoutTestsHelper.getXIDForMultipleItemToCartAuto(login, password, skuId,
				"", false, false, false, true);
		for (String skuAndQuantity : skuId) {
			String[] skuAndQuantitySplit = skuAndQuantity.split(":");
			String sku = skuAndQuantitySplit[0].trim();
			String ItemId = checkoutTestsHelper.getItemIdFromSKUId(requestGenerator, sku,
					CheckoutDataProviderEnum.CART.name());
			String tryAndBuyFlag = checkoutTestsHelper.getTryAndBuyFlagFromSKUId(requestGenerator, sku,
					CheckoutDataProviderEnum.CART.name());
			Assert.assertNotNull(ItemId);
			log.info("tryAndBuyFlag Value  :" + tryAndBuyFlag);
			Assert.assertEquals("true", tryAndBuyFlag, "TryAndBuy Flag value should be same:");
		}
		orderID = end2EndHelper2.createOrder(login, password, BountyTestcasesConstants.Pincodes.PINCODE_560068, skuId, "", false, true, false, "", true);
		log.info("Order Created :" + orderID);
		OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
		String storeOrderID = orderEntry.getStoreOrderId();

		for (String skuAndQuantity : skuId) {
			String[] skuAndQuantitySplit = skuAndQuantity.split(":");
			String sku = skuAndQuantitySplit[0].trim();

			String query = "Select is_try_and_buy from xcart_order_details where orderid in (select orderid from xcart_orders where order_number='"+ storeOrderID +"') and sku_id='" + sku + "';";
			List SelectedRecodes = bountyServiceHelperMT.getxCartOrderDetailsTableDBEntry(storeOrderID, query);
			HashMap<String, Integer> txResult = (HashMap<String, Integer>) SelectedRecodes.get(0);
			for (Map.Entry<Long, Boolean> m : hm.entrySet()) {
				if (m.getKey().equals(Long.parseLong(sku))) {
					if (Boolean.TRUE == m.getValue()) {
						Assert.assertEquals(txResult.get("is_try_and_buy").intValue(), 1,
								m.getKey() + ":TryAndBuy Flag value should be same");
					} else if (Boolean.FALSE == m.getValue()) {
						Assert.assertEquals(txResult.get("is_try_and_buy").intValue(), 0,
								m.getKey() + ":TryAndBuy Flag value should be same");
					}
				}
			}

		}

	}

	@Test(enabled = true, groups = { "regression",
	"tryandbuy" }, description = "if order placed with one TnB one Non TnB Skus , TnB flag should be there in bounty table myntra_order.xcart_order_details.")
	public void verifyTheIsTryAndBuyFlagInXcartdOrderDetailsTabelIsTryAndBuyFlagForOrder() throws Exception {

		String orderID;
		String skuId[] = { BountyTestcasesConstants.BountyServiceTest.verifyTheIsTryAndBuyFlagInXcartdOrderDetailsTabelIsTryAndBuyFlagForOrder_ITEM1+":3" };
		HashMap<Long, Boolean> hm = new HashMap<Long, Boolean>();
		hm.put((long) 3856, Boolean.TRUE);

		orderID = end2EndHelper.createOrder(login, password, BountyTestcasesConstants.Pincodes.PINCODE_560068, skuId, "", false, true, false, "", true);
		System.out.println("Order Created :" + orderID);
		OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
		String storeOrderID = orderEntry.getStoreOrderId();

		for (String skuAndQuantity : skuId) {
			String[] skuAndQuantitySplit = skuAndQuantity.split(":");
			String sku = skuAndQuantitySplit[0].trim();
			List SelectedRecodes = bountyServiceHelperMT.getxCartOrderDetailsTableDBEntry(storeOrderID);

			HashMap<String, Integer> txResult = (HashMap<String, Integer>) SelectedRecodes.get(0);
			log.info("TryAndBuy :" + txResult.get("is_try_and_buy"));
			for (Map.Entry<Long, Boolean> m : hm.entrySet()) {
				if (m.getKey().equals(Long.parseLong(sku))) {
					if (Boolean.TRUE == m.getValue()) {
						Assert.assertEquals(txResult.get("is_try_and_buy").intValue(), 1,
								m.getKey() + ":TryAndBuy Flag value should be same");
					} else if (Boolean.FALSE == m.getValue()) {
						Assert.assertEquals(txResult.get("is_try_and_buy").intValue(), 0,
								m.getKey() + ":TryAndBuy Flag value should be same");
					}
				}
			}
		}
	}

	@Test(enabled = true, groups = { "regression",
	"address" }, description = "verify Adress SwitchTo Default Address If Fetch Through Address ID Failed And FG Is On")
	public void verifyAdressSwitchToDefaultAddressIfFetchThroughAddressIDFailedAndFGIsOn() throws Exception {
		end2EndHelper.updateToolsApplicationProperties("bounty.get_default_address", "true", "oms");
		bountyServiceHelperMT.refreshBountyApplicationPropertyCache();
		String[] skuID = { BountyTestcasesConstants.BountyServiceTest.verifyAdressSwitchToDefaultAddressIfFetchThroughAddressIDFailedAndFGIsOn_ITEM1+":1" };
		String uidx = ideaApiHelper.getUIDXForLoginViaEmail("myntra", login);
		HashMap<String, Object> orderEntry1 = bountyServiceHelperMT.getCreateOrderEntryWithCoupon(login, BountyTestcasesConstants.BountyServiceTest.PASSWORD, skuID,
				"ADD", "", false, false, false);
		OrderResponse orderResponse1 = bountyServiceHelperMT.createOrder((CartEntry) orderEntry1.get("cart"), "12345",
				uidx, "" + orderEntry1.get("xid"), "0", "0", "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068, "responsive", "DEFAULT", "on");
		Assert.assertEquals(orderResponse1.getStatus().getStatusType(), Type.SUCCESS,
				"Address should be fall back to Default Address");
		// To-DO Need to validate the address id stored in the DB once its
		// implemented
	}

	@Test(enabled = false, groups = { "regression",
	"address" }, description = "verify Fetch Address Should not Fetch Default Address If FG Is OFF")
	public void verifyFetchAddressShouldnotFetchDefaultAddressIfFGIsOFF() throws Exception {
		end2EndHelper.updateToolsApplicationProperties("bounty.get_default_address", "false", "oms");
		bountyServiceHelperMT.refreshBountyApplicationPropertyCache();
		String[] skuID = { BountyTestcasesConstants.BountyServiceTest.verifyFetchAddressShouldnotFetchDefaultAddressIfFGIsOFF_ITEM1+":1" };
		HashMap<String, Object> orderEntry1 = bountyServiceHelperMT.getCreateOrderEntryWithCoupon(login, BountyTestcasesConstants.BountyServiceTest.PASSWORD, skuID,
				"ADD", "", false, false, false);
		OrderResponse orderResponse1 = bountyServiceHelperMT.createOrder((CartEntry) orderEntry1.get("cart"), "12345",
				uidx, "" + orderEntry1.get("xid"), "0", "0", "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068, "responsive", "DEFAULT", "on");
		Assert.assertEquals(orderResponse1.getStatus().getStatusType(), Type.ERROR,
				"Address should not fall back to Default Address if feature gate is off");
		Assert.assertEquals(orderResponse1.getStatus().getStatusMessage(),
				"Error fetching address for address id 28649",
				"Address should not fall back to Default Address if feature gate is off");
	}

	@Test(enabled = true, groups = { "regression", "address" }, description = "Verify Address as per the address id passed from Cart")
	public void verifyAddressShouldBeStoredInBounty() throws Exception {
		end2EndHelper.updateToolsApplicationProperties("bounty.get_default_address", "false", "oms");
		bountyServiceHelperMT.refreshBountyApplicationPropertyCache();
		String[] skuID = { BountyTestcasesConstants.BountyServiceTest.verifyAddressShouldBeStoredInBounty_ITEM1+":1" };
		HashMap<String, Object> orderEntry1 = bountyServiceHelperMT.getCreateOrderEntryWithCoupon(login, BountyTestcasesConstants.BountyServiceTest.PASSWORD, skuID,
				"ADD", "", false, false, false);
		OrderResponse orderResponse1 = bountyServiceHelperMT.createOrder((CartEntry) orderEntry1.get("cart"), "12345",
				uidx, "" + orderEntry1.get("xid"), "0", "0", "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068, "responsive", "DEFAULT", "on");

		Assert.assertEquals(orderResponse1.getStatus().getStatusType(), Type.SUCCESS,
				"Address should not fall back to Default Address if feature gate is off");

		OrderEntry orderEntry = orderResponse1.getData().iterator().next();
		HashMap listOrder = bountyServiceHelperMT.getxCartOrderTableDBEntry(orderEntry.getStoreOrderId());
		Assert.assertEquals(listOrder.get("address_id").toString(), BountyTestcasesConstants.Pincodes.PINCODE_560068,
				"Address ID should be saved in the DB");
	}

	@Test(enabled = true, groups = { "regression", "address" }, description = "Verify DB Details for Normal Order with coupon")
	public void verifyDBDetailsForNormalOrderWithCoupon() throws Exception {

		uidx = ideaApiHelper.getUIDXForLoginViaEmail("myntra", login);
		bountyServiceHelperMT.createNewCoupon(uidx, "bounty001", 20);

		String skuId1[] = { BountyTestcasesConstants.BountyServiceTest.verifyDBDetailsForNormalOrder_ITEM1+":2" };
		HashMap<String, Object> orderEntry = bountyServiceHelperMT.getCreateOrderEntryWithCoupon(login, BountyTestcasesConstants.BountyServiceTest.PASSWORD, skuId1,
				"ADD", "bounty001", false, false, false);
		OrderResponse orderResponse = bountyServiceHelperMT.createOrder((CartEntry) orderEntry.get("cart"), "12345",
				login, "" + orderEntry.get("xid"), "0", "0", "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068, "responsive", "DEFAULT", "on");
		Assert.assertEquals(orderResponse.getStatus().getStatusType(), StatusResponse.Type.SUCCESS);
		Assert.assertEquals(orderResponse.getStatus().getStatusCode(), 1001);
		Assert.assertEquals(orderResponse.getStatus().getStatusMessage(), "Order added successfully");

		String storeOrderId = orderResponse.getData().get(0).getStoreOrderId();
		log.info("Store Order ID:"+storeOrderId);

		OrderCreationResponse orderQueueResponse = bountyServiceHelperMT.queueOrder(
				orderResponse.getData().get(0).getStoreOrderId(), "" + orderEntry.get("xid"), login, "DEFAULT", "SUCCESS", false);
		Assert.assertEquals(orderQueueResponse.getStatus().getStatusType(), StatusResponse.Type.SUCCESS);
		Assert.assertEquals(orderQueueResponse.getStatus().getStatusCode(), 1001);
		Assert.assertEquals(orderQueueResponse.getStatus().getStatusMessage(), "Order added successfully");
		omsServiceHelper.checkReleaseStatusForOrder(omsServiceHelper.getOrderId(storeOrderId), "WP");
		//DB Validation for xcart_orders table
		HashMap<String, Object> hashmapOrderDetail = bountyServiceHelperMT.getxCartOrderTableDBEntry(storeOrderId);

		Assert.assertNotNull(hashmapOrderDetail.get("orderid"), "Order Id is null");
		Assert.assertEquals(hashmapOrderDetail.get("login"), login,"login is not correct in DB");
		Assert.assertEquals(hashmapOrderDetail.get("status"), "Q","order status should be PP");
		Assert.assertEquals(hashmapOrderDetail.get("payment_method"), "on","payment method is not correct in DB");
		Assert.assertEquals(hashmapOrderDetail.get("address_id").toString(), BountyTestcasesConstants.Pincodes.PINCODE_560068, "Address id is not correct");
		Assert.assertEquals(hashmapOrderDetail.get("ordertype"), "on","order type is not correct in DB");
		Assert.assertEquals(hashmapOrderDetail.get("shipping_method"), "NORMAL","Shipping method is not correct in DB");
		Assert.assertEquals(hashmapOrderDetail.get("pps_id").toString(), "12345","ppsid is not in DB");
		Assert.assertEquals(hashmapOrderDetail.get("coupon").toString(), "bounty001","coupon is not used");
		Assert.assertTrue(Double.parseDouble(hashmapOrderDetail.get("total").toString())>0,"total amount is not available in DB");
		Assert.assertTrue(Double.parseDouble(hashmapOrderDetail.get("subtotal").toString())>0,"subtotal field is 0 in DB");
		Assert.assertTrue(Double.parseDouble(hashmapOrderDetail.get("coupon_discount").toString())>0,"discount is not applied");

		//DB Validation for xcart_order_details table
		totalLines  = bountyServiceHelperMT.getxCartOrderDetailsTableDBEntry(storeOrderId);
		hashmapOrderDetail = (HashMap<String, Object>) totalLines.get(0);
		Assert.assertNotNull(hashmapOrderDetail.get("orderid"), "Order Id is null");
		Assert.assertTrue(Double.parseDouble(hashmapOrderDetail.get("price").toString())>0,"price is 0 in DB");
		Assert.assertEquals(hashmapOrderDetail.get("product_style").toString(), "1531","Product style is not same");
		Assert.assertEquals(hashmapOrderDetail.get("supply_type").toString(), "ON_HAND","Supply Type is not same");
		Assert.assertEquals(hashmapOrderDetail.get("packaging_type").toString(), "NORMAL","packaging type is not same");
		Assert.assertTrue(Double.parseDouble(hashmapOrderDetail.get("coupon_discount_product").toString())>0,"discount is not applied");
		Assert.assertEquals(hashmapOrderDetail.get("sku_id").toString(), "3831","SKU ID is not same");

		//DB Validation for Orders table in OMS
		totalLines = omsServiceHelper.getOrderEntryDB(storeOrderId);
		HashMap<String, Object> hashmapOrderDetailOMS = 
				(HashMap<String, Object>) totalLines.get(0);
		log.info("Values: "+hashmapOrderDetailOMS.get("id")+" "+hashmapOrderDetailOMS.get("login")
		+" "+hashmapOrderDetailOMS.get("customer_name"));
		Assert.assertNotNull(hashmapOrderDetailOMS.get("id"), "Order Id is null");
		Assert.assertEquals(hashmapOrderDetailOMS.get("login"), "bountytestuser001@myntra.com","login is not correct");
		Assert.assertEquals(hashmapOrderDetailOMS.get("customer_name"), "BountyProfile","customer name is not correct");
		Assert.assertEquals(hashmapOrderDetailOMS.get("coupon_code"), "bounty001","login is not correct");
		Assert.assertTrue(Double.parseDouble(hashmapOrderDetailOMS.get("mrp_total").toString()) >0,"mrp total should be greater than 0");
		Assert.assertTrue(Double.parseDouble(hashmapOrderDetailOMS.get("coupon_discount").toString())>0,"coupon discount should be greater than 0");

		//DB Validation for Order Release table in OMS
		totalLines = omsServiceHelper.getOrderReleaseDBEntry(storeOrderId);
		HashMap<String, Object> hashmapOrderReleaseDetailOMS = 
				(HashMap<String, Object>) totalLines.get(0);
		Assert.assertNotNull(hashmapOrderReleaseDetailOMS.get("id"), "Order Id is null");
		Assert.assertEquals(hashmapOrderReleaseDetailOMS.get("login"), "bountytestuser001@myntra.com","login is not correct");
		Assert.assertEquals(hashmapOrderReleaseDetailOMS.get("email"), "bountytestuser001@myntra.com","email is not correct");
		Assert.assertEquals(hashmapOrderReleaseDetailOMS.get("zipcode"), "560068","Zipcode is not correct");

		//DB Validation for order Line in OMS
		orderId = omsServiceHelper.getOrderEntryByStoreOrderID(storeOrderId).getId().toString();
		totalLines = omsServiceHelper.getOrderLineDBEntry(""+orderId);
		HashMap<String, Object> hashmapOrderLineDetailOMS = 
				(HashMap<String, Object>) totalLines.get(0);
		Assert.assertNotNull(hashmapOrderLineDetailOMS.get("id"), "Line Id is null");
		Assert.assertEquals(hashmapOrderLineDetailOMS.get("style_id").toString(), "1531","style is not correct");
		Assert.assertEquals(hashmapOrderLineDetailOMS.get("sku_id").toString(), "3831","skuid is not correct");

	}

	@Test(enabled = true, groups = { "regression", "address" }, description = "Verify DB Details for Normal Order with coupon")
	public void verifyOrderCreationFailWithoutAddress() throws Exception {

		uidx = ideaApiHelper.getUIDXForLoginViaEmail("myntra", login);
		bountyServiceHelperMT.createNewCoupon(uidx, "bounty001", 20);

		String skuId1[] = { BountyTestcasesConstants.BountyServiceTest.queueAlreadyQueuedOrderDP_ITEM1+":2" };
		HashMap<String, Object> orderEntry = bountyServiceHelperMT.getCreateOrderEntryWithCoupon(login, BountyTestcasesConstants.BountyServiceTest.PASSWORD, skuId1,
				"ADD", "bounty001", false, false, false);
		OrderResponse orderResponse = bountyServiceHelperMT.createOrder((CartEntry) orderEntry.get("cart"), "12345",
				login, "" + orderEntry.get("xid"), "0", "0", "0.0", "", "responsive", "DEFAULT", "on");
		Assert.assertEquals(orderResponse.getStatus().getStatusType(), StatusResponse.Type.ERROR);
	}

	@Test(enabled = true, groups = { "regression", "address" }, description = "Verify DB Details for Normal Order with coupon")
	public void verifyOrderCreationFailWithoutLogin() throws Exception {

		uidx = ideaApiHelper.getUIDXForLoginViaEmail("myntra", login);
		bountyServiceHelperMT.createNewCoupon(uidx, "bounty001", 20);

		String skuId1[] = { BountyTestcasesConstants.BountyServiceTest.queueAlreadyQueuedOrderDP_ITEM1+":2" };
		HashMap<String, Object> orderEntry = bountyServiceHelperMT.getCreateOrderEntryWithCoupon(login, BountyTestcasesConstants.BountyServiceTest.PASSWORD, skuId1,
				"ADD", "bounty001", false, false, false);
		OrderResponse orderResponse = bountyServiceHelperMT.createOrder((CartEntry) orderEntry.get("cart"), "12345",
				"", "" + orderEntry.get("xid"), "0", "0", "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068, "responsive", "DEFAULT", "on");
		Assert.assertEquals(orderResponse.getStatus().getStatusType(), StatusResponse.Type.ERROR);
	}

	@Test(enabled = true, groups = { "regression", "address" }, description = "Verify DB Details for Normal Order with coupon")
	public void verifyOrderCreationFailWithoutLoginAndAddress() throws Exception {

		uidx = ideaApiHelper.getUIDXForLoginViaEmail("myntra", login);
		bountyServiceHelperMT.createNewCoupon(uidx, "bounty001", 20);

		String skuId1[] = { BountyTestcasesConstants.BountyServiceTest.queueAlreadyQueuedOrderDP_ITEM1+":2" };
		HashMap<String, Object> orderEntry = bountyServiceHelperMT.getCreateOrderEntryWithCoupon(login, BountyTestcasesConstants.BountyServiceTest.PASSWORD, skuId1,
				"ADD", "bounty001", false, false, false);
		OrderResponse orderResponse = bountyServiceHelperMT.createOrder((CartEntry) orderEntry.get("cart"), "12345",
				"", "" + orderEntry.get("xid"), "0", "0", "0.0", "", "responsive", "DEFAULT", "on");
		Assert.assertEquals(orderResponse.getStatus().getStatusType(), StatusResponse.Type.ERROR);
	}

	@Test(enabled=false,groups = { "Regression", "MiniRegression", "ExhaustiveRegression",
	"PPS" }, description = "Create Order while CartEntry is empty")
	public void bountyServiceCreateOrderBlankSupplyType() throws IOException, JAXBException {
		String skuId1[] = {
				BountyTestcasesConstants.BountyServiceTest.bountyServiceCreateOrderBlankSupplyType_ITEM1 + ":1" };
		//		atpServiceHelper.updateInventoryDetailsForSeller(
		//				new String[] { BountyTestcasesConstants.BountyServiceTest.bountyServiceCreateOrderBlankSupplyType_ITEM1
		//						+ ":" + 28 + ":10000:0:" + sellerId + ":1" },
		//				"");

		HashMap<String, Object> orderEntry = bountyServiceHelperMT.getCreateOrderEntry(login,
				BountyTestcasesConstants.BountyServiceTest.PASSWORD, skuId1, "ADD", "", "200", "50", "");
		//Order Entry
		//log.info("CartEntry: "+(CartEntry)orderEntry.get("cart"));

		CartEntry cartEntry = (CartEntry) orderEntry.get("cart");
		log.info("KeyValue:"+cartEntry.getCartItemEntries().get(0).toString());
		log.info("CartEntry: "+APIUtilities.getObjectToJSON(cartEntry));
		OrderResponse orderResponse = bountyServiceHelperMT.createOrder((CartEntry) orderEntry.get("cart"),
				BountyTestcasesConstants.BountyServiceTest.PASSWORD, login, ""+orderEntry.get("xid"), "0", "0", "0.0",
				BountyTestcasesConstants.Pincodes.PINCODE_560068, "responsive", "DEFAULT", "on");
		Assert.assertEquals(orderResponse.getStatus().getStatusType(), StatusResponse.Type.ERROR);
		Assert.assertEquals(orderResponse.getStatus().getStatusMessage(), "Invalid session");
	}

	@Test(enabled=true, description = "Create order using JIT SKU")
	public void createOrderOfJitSKU() throws Exception{


		String skuId[] = {BountyTestcasesConstants.BountyServiceTest.bountyServiceCreateOrderJIT_ITEM1+":1"};

		String orderID = end2EndHelper.createOrder(login, password, BountyTestcasesConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
		OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
		omsServiceHelper.checkReleaseStatusForOrder(orderID, "Q:WP");	        

	}

	@Test(enabled=true, description = "Create order with shipping and GiftWrap charges")
	public void createOrderWithShippingAndGiftWrapCharges() throws Exception{


		String skuId[] = {BountyTestcasesConstants.BountyServiceTest.verifyDBDetailsForNormalOrder_ITEM1+":1"};

		String orderID = end2EndHelper.createOrder(login, password, BountyTestcasesConstants.Pincodes.PINCODE_560068, skuId, "", false, false, true, "", false);
		OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
		omsServiceHelper.checkReleaseStatusForOrder(orderID, "Q:WP");
		OrderReleaseEntry orderReleaseEntry = orderEntry.getOrderReleases().get(0);
		Double shippingCharges = orderReleaseEntry.getShippingCharge();
		Double giftCharges = orderReleaseEntry.getGiftCharge();
		Assert.assertEquals(shippingCharges, 2.00,"Shipping charge is incorrect");
		Assert.assertEquals(giftCharges, 25.00,"Gift charge is incorrect");

	}
	
	@Test(groups = { "Regression", "MiniRegression", "ExhaustiveRegression",
	"PPS" }, description = "Decline Order When Order is in PP Status")
	public void declineOrderSuccessFromPPStatusAndSendNotificationAndSMS() throws IOException, JAXBException, ManagerException {

		String login = login_notify;
        String password = password_notify;
        String skuId1[] = {BountyTestcasesConstants.BountyServiceTest.declineOrderSuccessFromPPStatusDP_ITEM1+":2"};

        HashMap<String, Object> orderEntry1 = bountyServiceHelperMT.getCreateOrderEntry(login, password, skuId1, "ADD","", "200", "50", "");
        OrderResponse orderResponse1 = bountyServiceHelperMT.createOrder((CartEntry) orderEntry1.get("cart"), "12345", login, ""+orderEntry1.get("xid"), "0", "0", "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068,
                                                                       "responsive", "DEFAULT", "on");
        String storeOrderID = orderResponse1.getData().get(0).getStoreOrderId();

		//Assert.assertEquals(bountyServiceHelperMT.getxCartOrderTableDBEntry(storeOrderID).get("status"), "PP");
		OrderCreationResponse orderDeclineResponse = bountyServiceHelperMT.declineOrder(storeOrderID, login, ""+orderEntry1.get("xid"),
				"DEFAULT", "FAILURE", 2, false);
		Assert.assertEquals(orderDeclineResponse.getStatus().getStatusType(), StatusResponse.Type.SUCCESS);
		Assert.assertEquals(orderDeclineResponse.getStatus().getStatusCode(), 1004);
		Assert.assertEquals(orderDeclineResponse.getStatus().getStatusMessage(), "Order updated successfully");
		Assert.assertEquals(bountyServiceHelperMT.getxCartOrderTableDBEntry(storeOrderID).get("status"), "D");
		String orderID = omsServiceHelper.getOrderEntryByStoreOrderID(storeOrderID).getId().toString();
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"D");
	}








}