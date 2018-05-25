/*package com.myntra.apiTests.erpservices.oms.Test;

import com.myntra.absolut.cart.client.response.entry.CartEntry;
import com.myntra.apiTests.common.Constants.EnumSCM;
import com.myntra.apiTests.common.Constants.ReleaseStatus;
import com.myntra.apiTests.common.ProcessOrder.Service.ProcessRelease;
import com.myntra.apiTests.common.entries.ReleaseEntry;
import com.myntra.apiTests.common.entries.ReleaseEntryList;
import com.myntra.apiTests.end2end.End2EndHelper;
import com.myntra.apiTests.erpservices.atp.ATPServiceHelper;
import com.myntra.apiTests.erpservices.bounty.BountyServiceHelper;
import com.myntra.apiTests.erpservices.ims.IMSServiceHelper;
import com.myntra.apiTests.erpservices.lms.Helper.LMSHelper;
import com.myntra.apiTests.erpservices.lms.Helper.LmsServiceHelper;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.erpservices.oms.dp.CancellationTillHubDP;
import com.myntra.apiTests.erpservices.rms.RMSServiceHelper;
import com.myntra.apiTests.erpservices.sellerapis.SellerConfig;
import com.myntra.apiTests.erpservices.wms.WMSServiceHelper;
import com.myntra.apiTests.portalservices.ideaapi.IdeaApiHelper;
import com.myntra.apiTests.portalservices.pps.PPSServiceHelper;
import com.myntra.bounty.client.response.OrderCreationResponse;
import com.myntra.client.wms.codes.utils.OrderReleaseStatus;
import com.myntra.commons.codes.StatusResponse;
import com.myntra.commons.codes.StatusResponse.Type;
import com.myntra.commons.exception.ManagerException;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.oms.client.entry.OrderEntry;
import com.myntra.oms.client.entry.OrderLineEntry;
import com.myntra.oms.client.entry.OrderReleaseEntry;
import com.myntra.oms.client.response.OrderReleaseResponse;
import com.myntra.oms.client.response.OrderResponse;
import com.myntra.paymentplan.domain.response.ExchangeOrderResponse;
import com.myntra.silkroute.client.response.RtoResponse;
import com.myntra.test.commons.testbase.BaseTest;
import com.myntra.worms.client.entry.OrderCaptureReleaseEntry;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.testng.Assert.assertEquals;


*//**
 * @author 17727
 *
 *//*
public class CancellationTillHubTest extends BaseTest {

	static Initialize init = new Initialize("/Data/configuration");
	String login = OMSTCConstants.CancellationTillHubTest.LOGIN_URL;
	String uidx;
	String password = OMSTCConstants.CancellationTillHubTest.PASSWORD;
	private static Long vectorSellerID;
	private static Logger log = Logger.getLogger(CancellationTillHubTest.class);

	End2EndHelper end2EndHelper = new End2EndHelper();
	OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
	WMSServiceHelper wmsServiceHelper = new WMSServiceHelper();
	IMSServiceHelper imsServiceHelper = new IMSServiceHelper();
	ATPServiceHelper atpServiceHelper = new ATPServiceHelper();
	RMSServiceHelper rmsServiceHelper = new RMSServiceHelper();
	PPSServiceHelper ppsServiceHelper = new PPSServiceHelper();
	LmsServiceHelper lmsServiceHelper =  new LmsServiceHelper();
	ProcessRelease processRelease = new ProcessRelease();
	LMSHelper lmsHelper = new LMSHelper();
	IdeaApiHelper ideaApiHelper = new IdeaApiHelper();
	BountyServiceHelper bountyServiceHelper = new BountyServiceHelper();
	private HashMap<String, int[]> inventoryCountInATPBeforePlacingOrder;
	private HashMap<String, int[]> inventoryCountInIMSBeforePlacingOrder;
	private int blockCountATPBefore;
	private int blockCountATPOrderBefore;
	private int blockCountIMSBefore;
	private int blockCountIMSOrderBefore;
	private String orderID;
	private boolean orderStatus;
	private boolean orderStatusPK;
	private HashMap<String, int[]> inventoryCountInATPAfterPlacingOrder;
	private HashMap<String, int[]> inventoryCountInIMSAfterPlacingOrder;
	private int blockCountATPAfter;
	private int blockCountIMSAfter;
	private OrderReleaseResponse cancellationRes;
	private OrderCaptureReleaseEntry orderCaptureReleaseEntry;
	private HashMap<String, int[]> inventoryCountInATPAfterCancellation;
	private HashMap<String, int[]> inventoryCountInIMSAfterCancellation;
	private int blockCountATPAfterCancellation;
	private int blockCountIMSAfterCancellation;
	private OrderResponse cancellationOrderRes;
	private OrderReleaseEntry orderReleaseEntry;
	private int[] blockCountATP;
	private int[] blockCountIMS;
	private HashMap<String, int[]> inventoryCountInIMSBeforePlacingOrder_3832;
	private HashMap<String, int[]> inventoryCountInIMSBeforePlacingOrder_3837;
	private HashMap<String, int[]> inventoryCountInIMSAfterPlacingOrder_3832;
	private HashMap<String, int[]> inventoryCountInIMSAfterPlacingOrder_3837;
	private HashMap<String, int[]> inventoryCountInIMSAfterCancellation_3832;
	private HashMap<String, int[]> inventoryCountInIMSAfterCancellation_3837;
	private List<OrderLineEntry> orderLineEntries;
	private String lineID;
	private OrderReleaseResponse responseAfterCancelLine;
	private OrderEntry orderEntry;
	private List<OrderReleaseEntry> orderReleaseEntries;
	private String orderReleaseID1;
	private String orderReleaseID2;
	private String releaseStatus1;
	private String releaseStatus2;
	private HashMap<String, Object> orderEntry1;
	private OrderResponse orderResponse1;
	private String orderReleaseId;
	private int blockCountATPOrderAfterCancellation;
	private int blockCountATPOrderAfter;
	private int blockCountIMSOrderAfterCancellation;
	private int blockCountIMSOrderAfter;
	private String orderReleaseId2;
	private String orderReleaseId3;
	private boolean orderReleaseStatus;
	private String errorMessage;
	private String lmsStatus;
	private ExchangeOrderResponse exchangeOrderResponse;
	SoftAssert sft;
	private String supplyTypeOnHand = "ON_HAND";


	@BeforeClass(alwaysRun = true)
	public void testBeforeClass() throws SQLException, JAXBException, Exception {
		DBUtilities.exUpdateQuery("UPDATE on_hold_reasons_master SET is_enabled=0 where id in (35, 23,37);",
				"myntra_oms");
		omsServiceHelper.refreshOMSApplicationPropertyCache();
		omsServiceHelper.refreshOMSJVMCache();
		vectorSellerID = SellerConfig.getSellerID(SellerConfig.SellerName.HANDH);
		//uidx = ideaApiHelper.getUIDXForLogin("myntra", login);

	}

	*//**
	 * 
	 * This function is get ATP and IMS inventory for itemId before placing order
	 * @param item1
	 * @throws SQLException
	 *//*
	public void verifyATPandIMSDetailBeforePlacingOrder(String item1) throws SQLException{

		inventoryCountInATPBeforePlacingOrder = atpServiceHelper
				.getAtpInvAndBlockOrderCount(new String[] { item1 });

		inventoryCountInIMSBeforePlacingOrder = imsServiceHelper
				.getIMSInvAndBlockOrderCount(new String[] { item1 }, "28", "1",""+vectorSellerID);

		blockCountATP = inventoryCountInATPBeforePlacingOrder.get(item1);
		blockCountATPBefore = blockCountATP[0];
		blockCountATPOrderBefore = blockCountATP[1];

		log.info("blockCountATPBefore:"+blockCountATPBefore+" "+blockCountATPOrderBefore);

		blockCountIMS = inventoryCountInIMSBeforePlacingOrder.get(item1);
		blockCountIMSBefore = blockCountIMS[0];
		blockCountIMSOrderBefore=blockCountIMS[1];

		log.info("blockCountIMSBefore:"+blockCountIMSBefore+" "+blockCountIMSOrderBefore);

		log.info("InventoryCheck");
	}

	*//**
	 * 
	 * This function is get ATP and IMS inventory for itemId after placing order
	 * @param item1
	 * @throws SQLException
	 *//*
	public void verifyATPandIMSDetailAfterPlacingOrder(String item1) throws SQLException{


		inventoryCountInATPAfterPlacingOrder = atpServiceHelper
				.getAtpInvAndBlockOrderCount(new String[] { item1});

		inventoryCountInIMSAfterPlacingOrder = imsServiceHelper
				.getIMSInvAndBlockOrderCount(new String[] { item1 }, "28", "1", ""+vectorSellerID);

		blockCountATP = inventoryCountInATPAfterPlacingOrder.get(item1);
		blockCountATPAfter = blockCountATP[0];
		blockCountATPOrderAfter = blockCountATP[1];

		blockCountIMS = inventoryCountInIMSAfterPlacingOrder.get(item1);

		inventoryCountInIMSAfterPlacingOrder = imsServiceHelper
				.getIMSInvAndBlockOrderCount(new String[] { item1 }, "28", "1", ""+vectorSellerID);

		blockCountATP = inventoryCountInATPAfterPlacingOrder.get(item1);
		blockCountATPAfter = blockCountATP[0];
		blockCountATPOrderAfter = blockCountATP[1];

		blockCountIMS = inventoryCountInIMSAfterPlacingOrder.get(item1);
		blockCountIMSAfter = blockCountIMS[0];
		blockCountIMSOrderAfter = blockCountIMS[1];
	}

	*//**
	 * 
	 * This function is get ATP and IMS inventory for itemId after cancellation
	 * @param item1
	 * @throws SQLException
	 *//*
	public void verifyATPandIMSDetailAfterCancelOrder(String item1) throws SQLException{
		inventoryCountInATPAfterCancellation = atpServiceHelper

				.getAtpInvAndBlockOrderCount(new String[] { item1 });
		inventoryCountInIMSAfterCancellation = imsServiceHelper
				.getIMSInvAndBlockOrderCount(new String[] { item1 }, "28", "1", ""+vectorSellerID);

		blockCountATP = inventoryCountInATPAfterCancellation.get(item1);
		blockCountATPAfterCancellation = blockCountATP[0];
		blockCountATPOrderAfterCancellation = blockCountATP[1];

		blockCountIMS = inventoryCountInIMSAfterCancellation.get(item1);
		inventoryCountInIMSAfterCancellation = imsServiceHelper
				.getIMSInvAndBlockOrderCount(new String[] { item1 }, "28", "1", ""+vectorSellerID);

		blockCountATP = inventoryCountInATPAfterCancellation.get(item1);
		blockCountATPAfterCancellation = blockCountATP[0];
		blockCountATPOrderAfterCancellation = blockCountATP[1];

		blockCountIMS = inventoryCountInIMSAfterCancellation.get(item1);
		blockCountIMSAfterCancellation = blockCountIMS[0];
		blockCountIMSOrderAfterCancellation = blockCountIMS[1];
	}

	*//**
	 * This function is to verify APT and IMS inventory count
	 * @param atpBefore
	 * @param atpAfter
	 * @param imsBefore
	 * @param imsAfter
	 * @param atpOrderBefore
	 * @param atpOrderAfter
	 * @param imsOrderBefore
	 * @param imsOrderAfter
	 *//*
	public void verifyAssert(int atpBefore, int atpAfter, int imsBefore, int imsAfter,int atpOrderBefore, int atpOrderAfter, int imsOrderBefore, int imsOrderAfter){
		sft.assertEquals(atpAfter, atpBefore, "ATP Count After Order Place");
		sft.assertEquals(imsAfter, imsBefore, "IMS Count After Order Place");
		sft.assertEquals(atpOrderAfter, atpOrderBefore, "ATP Count After Order Place");
		sft.assertEquals(imsOrderAfter, imsOrderBefore, "IMS Count After Order Place");
	}

	*//**
	 * 
	 * This function is to verify if cancellation of release is successful
	 * @param orderReleaseId
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 *//*
	public void verifyCancelReleaseSuccess(String orderReleaseId) throws UnsupportedEncodingException, JAXBException{
		cancellationRes = omsServiceHelper.cancelOrderRelease(orderReleaseId, "1", login, "TestOrder Cancellation");
		sft.assertEquals(cancellationRes.getStatus().getStatusCode(), 1006,"status code is Incorrect");
		sft.assertEquals(cancellationRes.getStatus().getStatusType(), Type.SUCCESS,"There is error in response");
		OrderReleaseEntry orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(orderReleaseId);
		sft.assertEquals(orderReleaseEntry.getStatus(), "F","Release status is not F");
		End2EndHelper.sleep(5000L);
		sft.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(orderReleaseId, "F", 5),"Release is not in F status");

	}
	
	public void verifyStatusInOrderCaptureSystem(String orderReleaseId,String status) throws UnsupportedEncodingException, JAXBException{
		// Verify Status in Order Capture System		
		HashMap<String, Object> orderCaptureReleaseEntry = (HashMap<String, Object>) wmsServiceHelper.getCaptureOrderReleaseEntry(orderReleaseId);
		String statusInDB  = orderCaptureReleaseEntry.get("order_release_status").toString();
		assertEquals(statusInDB, status,"status should be "+status+"but actual:"+statusInDB);
	}
	
	*//**
	 * 
	 * This function is to verify if cancellation of release is successful for WP
	 * @param orderReleaseId
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 *//*
	public void verifyCancelReleaseSuccessForWP(String orderReleaseId) throws UnsupportedEncodingException, JAXBException{
		cancellationRes = omsServiceHelper.cancelOrderRelease(orderReleaseId, "1", login, "TestOrder Cancellation");
		sft.assertEquals(cancellationRes.getStatus().getStatusCode(), 1006,"status code is Incorrect");
		sft.assertEquals(cancellationRes.getStatus().getStatusType(), Type.SUCCESS,"There is error in response");
		OrderReleaseEntry orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(orderReleaseId);
		sft.assertEquals(orderReleaseEntry.getStatus(), "F","Release status is not F");
		End2EndHelper.sleep(5000L);
		sft.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(orderReleaseId, "F", 5),"Release is not in F status");

		// Verify Status in Order Capture System
		verifyStatusInOrderCaptureSystem(orderReleaseId,OrderReleaseStatus.CANCELLED.toString());
	}

	*//**
	 * 
	 * This function is to verify if cancellation of order is successful
	 * @param
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 *//*
	public void verifyCancelOrderSuccess(String orderID) throws UnsupportedEncodingException, JAXBException{
		OrderResponse cancellationRes = omsServiceHelper.cancelOrder(orderID, "1", "erpadmin",
				"TestOrder Cancellation");
		sft.assertEquals(cancellationRes.getStatus().getStatusCode(), 1002,"status code is not correct");
		sft.assertEquals(cancellationRes.getStatus().getStatusType(), Type.SUCCESS, "Verify Response Status Type");
		sft.assertEquals(cancellationRes.getData().get(0).getOrderReleases().get(0).getStatus(), "F",
				"Order Status should be F");
	}

	*//**
	 * This function is to verify if cancellation of release is Failed
	 * @param
	 * @param orderOrRelease
	 * @param errorCode
	 * @param errorMsg
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 *//*
	public void verifyCancelFailure(String orderID, String orderOrRelease,int errorCode, String errorMsg) throws UnsupportedEncodingException, JAXBException{
		if(orderOrRelease.equalsIgnoreCase("order")){
			cancellationOrderRes = omsServiceHelper.cancelOrder(orderID, "1", "erpadmin",
					"TestOrder Cancellation");
			isCancelAssertFailure(errorCode, errorMsg,orderOrRelease);
		}else if(orderOrRelease.equalsIgnoreCase("release")){
			orderEntry = omsServiceHelper.getOrderEntry(orderID);
			orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
			cancellationRes = omsServiceHelper.cancelOrderRelease(orderReleaseId, "1", login, "TestOrder Cancellation");
			isCancelAssertFailure(errorCode, errorMsg,orderOrRelease);

		}else{
			log.info("Enter correct input!!!");
		}
	}

	*//**
	 * This function is to verify if Assert is Failed
	 * @param statusCode
	 * @param errorMsg
	 * @param orderOrRelease
	 *//*
	public void isCancelAssertFailure(int statusCode,String errorMsg,String orderOrRelease){
		if(orderOrRelease.equalsIgnoreCase("order")){
			sft.assertEquals(cancellationOrderRes.getStatus().getStatusCode(), statusCode,"status code should be "+statusCode);
			sft.assertEquals(cancellationOrderRes.getStatus().getStatusType(), Type.ERROR,"There should be error in response");
			//Assert.assertEquals(cancellationOrderRes.getStatus().getStatusMessage(),errorMsg,"There is should be error message: "+errorMsg+" but found: "+cancellationOrderRes.getStatus().getStatusMessage());
		}else if(orderOrRelease.equalsIgnoreCase("release")){
			sft.assertEquals(cancellationRes.getStatus().getStatusCode(), statusCode,"status code should be "+statusCode);
			sft.assertEquals(cancellationRes.getStatus().getStatusType(), Type.ERROR,"There should be error in response");
			//Assert.assertEquals(cancellationRes.getStatus().getStatusMessage(),errorMsg,"There is should be error message: "+errorMsg+" but found: "+cancellationRes.getStatus().getStatusMessage());			
		}
	}

	*//**
	 * This function is to verify if Assert is SuccessFull
	 * @param statusCode
	 * @param successMessage
	 * @param orderOrRelease
	 *//*
	public void isCancelAssertSuccess(int statusCode,String successMessage,String orderOrRelease){
		if(orderOrRelease.equalsIgnoreCase("order")){
			sft.assertEquals(cancellationOrderRes.getStatus().getStatusCode(), statusCode,"status code should be "+statusCode);
			sft.assertEquals(cancellationOrderRes.getStatus().getStatusType(), Type.SUCCESS,"There is error in response");
			sft.assertEquals(cancellationOrderRes.getStatus().getStatusMessage(),successMessage,"There is should be message: "+successMessage+" but found: "+cancellationOrderRes.getStatus().getStatusMessage());
		}else if(orderOrRelease.equalsIgnoreCase("release")){
			sft.assertEquals(cancellationRes.getStatus().getStatusCode(), statusCode,"status code should be "+statusCode);
			sft.assertEquals(cancellationRes.getStatus().getStatusType(), Type.SUCCESS,"There is error in response");
			sft.assertEquals(cancellationRes.getStatus().getStatusMessage(),successMessage,"There is should be message: "+successMessage+" but found: "+cancellationRes.getStatus().getStatusMessage());

		}
	}
	
	
	public void createOrderInPKStatusInLMS(String releaseId,String status) throws JAXBException, JsonGenerationException, org.codehaus.jackson.map.JsonMappingException, IOException{
		//create order in packed status in LMS
		omsServiceHelper.stampGovtTaxForVectorSuccess(releaseId);
		omsServiceHelper.updateReleaseStatusDB(releaseId, "PK");
		orderLineEntries = omsServiceHelper.getOrderLineEntries(releaseId);
		for(OrderLineEntry orderLineEntry: orderLineEntries){
			omsServiceHelper.updateOrderLineStatusDB(orderLineEntry.getId().toString(), "QD");
		}
		String trackingNumber = lmsServiceHelper.getTrackingNumber("ML","36","true", "560068","NORMAL").getTrackingNumberEntry().getTrackingNumber().toString();
		
    	String query = "update order_release set courier_code='ML',tracking_no='"+trackingNumber+"' where id = '"+releaseId+"';";
		DBUtilities.exUpdateQuery(query, "myntra_oms");
		end2EndHelper.sleep(5000L);
		omsServiceHelper.pushReleaseToLms(releaseId);
		sft.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(""+releaseId, EnumSCM.PACKED,12),"Release status is not in PK status");
		updateStatusInCaptureOrderRelease(releaseId);
	}
	
	public void updateStatusInCaptureOrderRelease(String releaseId){
		String query = "update capture_order_release set order_release_status='COMPLETED' where portal_order_release_id = '"+releaseId+"';";
		DBUtilities.exUpdateQuery(query, "myntra_wms");
		
	}
	
	public void updateReleaseStatusInLMS(String releaseId,String status,String shipmentStatus){
		String query = "update order_to_ship set `status`='"+status+"', shipment_status='"+shipmentStatus+"' where order_id = '"+releaseId+"';";
		DBUtilities.exUpdateQuery(query, "myntra_lms");
	}


	//Passed2
	@Test(enabled = true,groups={"regression","sanity","cancellationtillHub","dockinstest"}, description = "Cancel Release order in PK state")
	public void cancelReleaseOrderInPKStatus() throws Exception {
		sft=new SoftAssert();
		String skuId[] = { OMSTCConstants.CancellationTillHubTest.cancelReleaseOrderInPKStatus_ITEM1+":1" };
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.CancellationTillHubTest.cancelReleaseOrderInPKStatus_ITEM1+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID+":1"}, supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.CancellationTillHubTest.cancelReleaseOrderInPKStatus_ITEM1+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID},
				supplyTypeOnHand);

		verifyATPandIMSDetailBeforePlacingOrder(OMSTCConstants.CancellationTillHubTest.cancelReleaseOrderInPKStatus_ITEM1);
		orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
		log.info("OrderID: "+orderID);
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
		orderEntry = omsServiceHelper.getOrderEntry(orderID);
		orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();

		End2EndHelper.sleep(10000L);

		verifyATPandIMSDetailAfterPlacingOrder(OMSTCConstants.CancellationTillHubTest.cancelReleaseOrderInPKStatus_ITEM1);

		// VerifyInventory in IMS and ATP After Order Placement
		verifyAssert(blockCountATPBefore,blockCountATPAfter,blockCountIMSBefore,blockCountIMSAfter,blockCountATPOrderBefore+1,
				blockCountATPOrderAfter,blockCountIMSOrderBefore+1,blockCountIMSOrderAfter);

		List<ReleaseEntry> releaseEntries = new ArrayList<>();
		releaseEntries.add(new ReleaseEntry.Builder(orderReleaseId, ReleaseStatus.PK).build());
		ReleaseEntryList releaseEntryList= new ReleaseEntryList(releaseEntries);
		processRelease.processReleaseToStatus(releaseEntryList);
		orderStatusPK = omsServiceHelper.validateReleaseStatusInOMS(orderReleaseId, "PK", 10);
		sft.assertTrue(orderStatusPK, "Order Status is not in PK");

		verifyATPandIMSDetailAfterPlacingOrder(OMSTCConstants.CancellationTillHubTest.cancelReleaseOrderInPKStatus_ITEM1);

		// VerifyInventory in IMS and ATP After Order Placement
		verifyAssert(blockCountATPBefore,blockCountATPAfter+1,blockCountIMSBefore,blockCountIMSAfter+1,blockCountATPOrderBefore,
				blockCountATPOrderAfter,blockCountIMSOrderBefore,blockCountIMSOrderAfter);

		LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();

		verifyCancelReleaseSuccess(orderReleaseId);

		// Verify Status in Order Capture System
		verifyStatusInOrderCaptureSystem(orderReleaseId,OrderReleaseStatus.COMPLETED.toString());
		
		// Verify Inventory After Cancellation
		verifyATPandIMSDetailAfterCancelOrder(OMSTCConstants.CancellationTillHubTest.cancelReleaseOrderInPKStatus_ITEM1);
		verifyAssert(blockCountATPAfter,blockCountATPAfterCancellation,blockCountIMSAfter,blockCountIMSAfterCancellation,blockCountATPOrderAfter,
				blockCountATPOrderAfterCancellation,blockCountIMSOrderAfter,blockCountIMSOrderAfterCancellation);
		sft.assertAll();
	}

	//Passed2
	@Test(enabled = true,groups={"regression","cancellationtillHub"}, description = "Cancel Release order in PK state")
	public void cancelReleaseOrderInISStatus() throws Exception {
		sft=new SoftAssert();
		String skuId[] = { OMSTCConstants.CancellationTillHubTest.cancelOrderInPKStatus_ITEM1+":1" };
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.CancellationTillHubTest.cancelOrderInPKStatus_ITEM1+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID+":1"}, supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.CancellationTillHubTest.cancelOrderInPKStatus_ITEM1+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID},
				supplyTypeOnHand);

		orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
		log.info("OrderID: "+orderID);
		orderEntry = omsServiceHelper.getOrderEntry(orderID);
		orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");

		createOrderInPKStatusInLMS(orderReleaseId, "PK");

		//Process order in LMS till Inscanned
		updateReleaseStatusInLMS(orderReleaseId, "IS", "INSCANNED");
		lmsStatus = lmsHelper.getOrderToShipStatus(""+orderReleaseId);
		sft.assertEquals(lmsStatus, "INSCANNED","Release is not in INSCANNED status");
		//  Cancel Release
		verifyCancelReleaseSuccess(orderReleaseId);
		sft.assertAll();
	}


	//Passed2
	@Test(enabled = true,groups={"regression","cancellationtillHub"}, description = "Cancel Release order in PK state")
	public void cancelReleaseOrderInAddedToMBStatus() throws Exception {
		sft=new SoftAssert();
		String skuId[] = { OMSTCConstants.CancellationTillHubTest.cancelOrderInPKStatus_ITEM1+":1" };
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.CancellationTillHubTest.cancelOrderInPKStatus_ITEM1+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID+":1"}, supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.CancellationTillHubTest.cancelOrderInPKStatus_ITEM1+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID},
				supplyTypeOnHand);

		orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
		log.info("OrderID: "+orderID);
		orderEntry = omsServiceHelper.getOrderEntry(orderID);
		orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
		
		createOrderInPKStatusInLMS(orderReleaseId, "PK");

		//Process order in LMS till Inscanned
		updateReleaseStatusInLMS(orderReleaseId, "IS", "ADDED_TO_MB");
		lmsStatus = lmsHelper.getOrderToShipStatus(""+orderReleaseId);
		sft.assertEquals(lmsStatus, "ADDED_TO_MB","Release is not in INSCANNED status");
		//  Cancel Release
		verifyCancelFailure(orderID, "release", 8252, "Cancellation not allowed for release id : "+orderReleaseId);
		sft.assertAll();

	}






	//Passed2
	@Test(enabled = true,groups={"regression","cancellationtillHub"}, description = "Cancel Release order in PK state")
	public void cancelOrderInPKStatus() throws Exception {
		sft=new SoftAssert();
		String skuId[] = { OMSTCConstants.CancellationTillHubTest.cancelOrderInPKStatus_ITEM1+":1" };
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.CancellationTillHubTest.cancelOrderInPKStatus_ITEM1+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID+":1"}, supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.CancellationTillHubTest.cancelOrderInPKStatus_ITEM1+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID},
				supplyTypeOnHand);

		verifyATPandIMSDetailBeforePlacingOrder(OMSTCConstants.CancellationTillHubTest.cancelOrderInPKStatus_ITEM1);

		orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
		log.info("OrderID: "+orderID);
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
		verifyATPandIMSDetailAfterPlacingOrder(OMSTCConstants.CancellationTillHubTest.cancelOrderInPKStatus_ITEM1);

		// VerifyInventory in IMS and ATP After Order Placement
		verifyAssert(blockCountATPBefore,blockCountATPAfter,blockCountIMSBefore,blockCountIMSAfter,blockCountATPOrderBefore+1,
				blockCountATPOrderAfter,blockCountIMSOrderBefore+1,blockCountIMSOrderAfter);

		orderEntry = omsServiceHelper.getOrderEntry(orderID);
		orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
		List<ReleaseEntry> releaseEntries = new ArrayList<>();
		releaseEntries.add(new ReleaseEntry.Builder(orderReleaseId, ReleaseStatus.PK).build());
		ReleaseEntryList releaseEntryList= new ReleaseEntryList(releaseEntries);
		processRelease.processReleaseToStatus(releaseEntryList);
		orderStatusPK = omsServiceHelper.validateReleaseStatusInOMS(orderReleaseId, "PK", 10);
		sft.assertTrue(orderStatusPK, "Order Status is not in PK");

		verifyATPandIMSDetailAfterPlacingOrder(OMSTCConstants.CancellationTillHubTest.cancelOrderInPKStatus_ITEM1);

		// VerifyInventory in IMS and ATP After Order Placement
		verifyAssert(blockCountATPBefore,blockCountATPAfter+1,blockCountIMSBefore,blockCountIMSAfter+1,blockCountATPOrderBefore,
				blockCountATPOrderAfter,blockCountIMSOrderBefore,blockCountIMSOrderAfter);
		verifyCancelOrderSuccess(orderID);

		// Verify Inventory After Cancellation
		verifyATPandIMSDetailAfterCancelOrder(OMSTCConstants.CancellationTillHubTest.cancelOrderInPKStatus_ITEM1);
		verifyAssert(blockCountATPAfter,blockCountATPAfterCancellation,blockCountIMSAfter,blockCountIMSAfterCancellation,blockCountATPOrderAfter,
				blockCountATPOrderAfterCancellation,blockCountIMSOrderAfter,blockCountIMSOrderAfterCancellation);
		sft.assertAll();
	}



	//Passed2
	@Test(enabled = true,groups={"regression","cancellationtillHub"}, description = "Cancel Multi Release order in PK state")
	public void cancelMultipleReleasesInPKStatus() throws Exception {
		sft=new SoftAssert();
		String skuId[] = { OMSTCConstants.CancellationTillHubTest.cancelMultipleReleasesInPKStatus_ITEM1+":1",
				OMSTCConstants.CancellationTillHubTest.cancelMultipleReleasesInPKStatus_ITEM2+":1" };
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.CancellationTillHubTest.cancelMultipleReleasesInPKStatus_ITEM1+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID+":1"
				, OMSTCConstants.CancellationTillHubTest.cancelMultipleReleasesInPKStatus_ITEM2+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID+":1"}, supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.CancellationTillHubTest.cancelMultipleReleasesInPKStatus_ITEM1+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID,
				OMSTCConstants.CancellationTillHubTest.cancelMultipleReleasesInPKStatus_ITEM2+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID},supplyTypeOnHand);

		orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
		log.info("OrderID: "+orderID);
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
		orderEntry = omsServiceHelper.getOrderEntry(orderID);
		orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
		orderReleaseId2 = orderEntry.getOrderReleases().get(1).getId().toString(); 

		createOrderInPKStatusInLMS(orderReleaseId, "PK");
		createOrderInPKStatusInLMS(orderReleaseId2, "PK");
		orderStatusPK = omsServiceHelper.validateReleaseStatusInOMS(orderReleaseId, "PK", 10);
		sft.assertTrue(orderStatusPK, "Order Status is not in PK");

		verifyCancelReleaseSuccess(orderReleaseId);

		verifyCancelReleaseSuccess(orderReleaseId2);
		sft.assertAll();

	}
	
	//Passed2
		@Test(enabled = true,groups={"regression","cancellationtillHub"}, description = "Cancel Multi Release order in PK state")
		public void cancelMultipleReleasesInPKStatusTnB() throws Exception {
			sft=new SoftAssert();
	        atpServiceHelper.updateInventoryDetailsForSeller(new String[] {OMSTCConstants.CancellationTillHubTest.cancelMultipleReleasesInPKStatusTnB_ITEM1+":28,36:100:0:"+vectorSellerID+":1",
	        		OMSTCConstants.CancellationTillHubTest.cancelMultipleReleasesInPKStatusTnB_ITEM2+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+vectorSellerID+":1"},supplyTypeOnHand);
	        imsServiceHelper.updateInventoryForSeller(new String[] {OMSTCConstants.CancellationTillHubTest.cancelMultipleReleasesInPKStatusTnB_ITEM1+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+vectorSellerID,
	        		OMSTCConstants.CancellationTillHubTest.cancelMultipleReleasesInPKStatusTnB_ITEM2+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+vectorSellerID},supplyTypeOnHand);



	        String orderID;
	        String skuId[] = {OMSTCConstants.CancellationTillHubTest.cancelMultipleReleasesInPKStatusTnB_ITEM1+":1", OMSTCConstants.CancellationTillHubTest.cancelMultipleReleasesInPKStatusTnB_ITEM2+":1"};
	        HashMap<Long, Boolean> hm = new HashMap<Long, Boolean>();
	        hm.put((long) 47342, Boolean.TRUE);
	        hm.put((long) 47343, Boolean.TRUE);

	        orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", true);	
	        log.info("OrderID: "+orderID);
			omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
			orderEntry = omsServiceHelper.getOrderEntry(orderID);
			orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
			orderReleaseId2 = orderEntry.getOrderReleases().get(1).getId().toString();
			List<ReleaseEntry> releaseEntries = new ArrayList<>();
			releaseEntries.add(new ReleaseEntry.Builder(orderReleaseId, ReleaseStatus.PK).build());
			releaseEntries.add(new ReleaseEntry.Builder(orderReleaseId2, ReleaseStatus.PK).build());
			ReleaseEntryList releaseEntryList= new ReleaseEntryList(releaseEntries);
			processRelease.processReleaseToStatus(releaseEntryList);
			orderStatusPK = omsServiceHelper.validateReleaseStatusInOMS(orderReleaseId, "PK", 10);
			sft.assertTrue(orderStatusPK, "Order Status is not in PK");

			verifyCancelReleaseSuccess(orderReleaseId);

			verifyCancelReleaseSuccess(orderReleaseId2);
			sft.assertAll();
		}

	//Passed2
	@Test(enabled = true,groups={"regression","cancellationtillHub"}, description = "Cancel Multi Release order in PK state")
	public void cancelReleasesInPKStatusForWarehouseNotAllowingCancellation() throws Exception {
		sft=new SoftAssert();
		String skuId[] = { OMSTCConstants.CancellationTillHubTest.cancelMultipleReleasesInPKStatus_ITEM1+":1"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.CancellationTillHubTest.cancelMultipleReleasesInPKStatus_ITEM1+":"+OMSTCConstants.WareHouseIds.warehouseId19_BG+":10000:0:"+vectorSellerID+":1"}, supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.CancellationTillHubTest.cancelMultipleReleasesInPKStatus_ITEM1+":"+OMSTCConstants.WareHouseIds.warehouseId19_BG+":10000:0:"+vectorSellerID},supplyTypeOnHand);

		orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
		log.info("OrderID: "+orderID);
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
		orderEntry = omsServiceHelper.getOrderEntry(orderID);
		orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();

		createOrderInPKStatusInLMS(orderReleaseId, "PK");
		
		orderStatusPK = omsServiceHelper.validateReleaseStatusInOMS(orderReleaseId, "PK", 10);
		sft.assertTrue(orderStatusPK, "Order Status is not in PK");

		verifyCancelFailure(orderID, "release", 8252, "Cancellation not allowed for release id : "+orderReleaseId);
		sft.assertAll();

	}

	//Passed2
	@Test(enabled = true,groups={"regression","cancellationtillHub"}, description = "Cancel Multi Release order in PK state")
	public void cancelOrderInPKStatusForWarehouseNotAllowingCancellation() throws Exception {
		sft=new SoftAssert();
		String skuId[] = { OMSTCConstants.CancellationTillHubTest.cancelMultipleReleasesInPKStatus_ITEM1+":1"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.CancellationTillHubTest.cancelMultipleReleasesInPKStatus_ITEM1+":"+OMSTCConstants.WareHouseIds.warehouseId19_BG+":10000:0:"+vectorSellerID+":1"}, supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.CancellationTillHubTest.cancelMultipleReleasesInPKStatus_ITEM1+":"+OMSTCConstants.WareHouseIds.warehouseId19_BG+":10000:0:"+vectorSellerID},supplyTypeOnHand);

		orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
		log.info("OrderID: "+orderID);
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
		orderEntry = omsServiceHelper.getOrderEntry(orderID);
		orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();

		createOrderInPKStatusInLMS(orderReleaseId, "PK");
		
		orderStatusPK = omsServiceHelper.validateReleaseStatusInOMS(orderReleaseId, "PK", 10);
		sft.assertTrue(orderStatusPK, "Order Status is not in PK");

		verifyCancelFailure(orderID, "order", 8053, "Cancellation not allowed for order id : "+orderID);
		sft.assertAll();

	}
	//Passed2
	@Test(enabled = true,groups={"regression","cancellationtillHub"}, description = "Cancel Multi Release order in PK state")
	public void cancelMultipleReleasesInPKStatusWhenOneReleaseINWP() throws Exception {
		sft=new SoftAssert();
		String skuId[] = { OMSTCConstants.CancellationTillHubTest.cancelMultipleReleasesInPKStatus_ITEM1+":1",
				OMSTCConstants.CancellationTillHubTest.cancelMultipleReleasesInPKStatus_ITEM2+":1" };
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.CancellationTillHubTest.cancelMultipleReleasesInPKStatus_ITEM1+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID+":1"
				, OMSTCConstants.CancellationTillHubTest.cancelMultipleReleasesInPKStatus_ITEM2+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID+":1"}, supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.CancellationTillHubTest.cancelMultipleReleasesInPKStatus_ITEM1+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID,
				OMSTCConstants.CancellationTillHubTest.cancelMultipleReleasesInPKStatus_ITEM2+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID},supplyTypeOnHand);

		orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
		log.info("OrderID: "+orderID);
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
		orderEntry = omsServiceHelper.getOrderEntry(orderID);
		orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
		orderReleaseId2 = orderEntry.getOrderReleases().get(1).getId().toString();


		createOrderInPKStatusInLMS(orderReleaseId, "PK");
		orderStatusPK = omsServiceHelper.validateReleaseStatusInOMS(orderReleaseId, "PK", 10);
		sft.assertTrue(orderStatusPK, "Order Status is not in PK");

		orderStatusPK = omsServiceHelper.validateReleaseStatusInOMS(orderReleaseId2, "WP", 10);
		sft.assertTrue(orderStatusPK, "Order Status is not in WP");

		//check isCancel for PK release
		cancellationRes =   omsServiceHelper.isCancellationAllowed(orderReleaseId);
		isCancelAssertSuccess(1046, "Cancellation allowed","release");
		
		//check isCancel for WP release
		cancellationRes =   omsServiceHelper.isCancellationAllowed(orderReleaseId2);
		isCancelAssertSuccess(1046, "Cancellation allowed","release");

		verifyCancelReleaseSuccess(orderReleaseId);
		verifyCancelReleaseSuccessForWP(orderReleaseId2);
		sft.assertAll();
	}

	//Passed2
	@Test(enabled = true,groups={"regression","cancellationtillHub"}, description = "Cancel Multi Release order in PK state")
	public void cancelMultipleReleasesInPKStatusWhenOneReleaseINSH() throws Exception {
		sft=new SoftAssert();
		String skuId[] = { OMSTCConstants.CancellationTillHubTest.cancelMultipleReleasesInPKStatus_ITEM1+":1",
				OMSTCConstants.CancellationTillHubTest.cancelMultipleReleasesInPKStatus_ITEM2+":1" };
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.CancellationTillHubTest.cancelMultipleReleasesInPKStatus_ITEM1+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID+":1"
				, OMSTCConstants.CancellationTillHubTest.cancelMultipleReleasesInPKStatus_ITEM2+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID+":1"}, supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.CancellationTillHubTest.cancelMultipleReleasesInPKStatus_ITEM1+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID,
				OMSTCConstants.CancellationTillHubTest.cancelMultipleReleasesInPKStatus_ITEM2+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID},supplyTypeOnHand);

		orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
		log.info("OrderID: "+orderID);
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
		orderEntry = omsServiceHelper.getOrderEntry(orderID);
		orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
		orderReleaseId2 = orderEntry.getOrderReleases().get(1).getId().toString();


		createOrderInPKStatusInLMS(orderReleaseId, "PK");
		orderStatusPK = omsServiceHelper.validateReleaseStatusInOMS(orderReleaseId, "PK", 10);
		sft.assertTrue(orderStatusPK, "Order Status is not in PK");

		omsServiceHelper.updateReleaseStatusDB(orderReleaseId2, "SH");
		orderStatusPK = omsServiceHelper.validateReleaseStatusInOMS(orderReleaseId2, "SH", 10);
		sft.assertTrue(orderStatusPK, "Order Status is not in SH");

		verifyCancelReleaseSuccess(orderReleaseId);
		sft.assertAll();
	}	


	//Passed2
	@Test(enabled = true,groups={"regression","cancellationtillHub"}, description = "Cancel Multi Release order in PK state")
	public void cancelOrderOfMultipleReleasesInPKStatus() throws Exception {
		sft=new SoftAssert();
		String skuId[] = { OMSTCConstants.CancellationTillHubTest.cancelOrderOfMultipleReleasesInPKStatus_ITEM1+":1", OMSTCConstants.CancellationTillHubTest.cancelOrderOfMultipleReleasesInPKStatus_ITEM2+":1" };
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.CancellationTillHubTest.cancelOrderOfMultipleReleasesInPKStatus_ITEM1+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID+":1", OMSTCConstants.CancellationTillHubTest.cancelOrderOfMultipleReleasesInPKStatus_ITEM2+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID+":1"}, supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.CancellationTillHubTest.cancelOrderOfMultipleReleasesInPKStatus_ITEM1+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID,
				OMSTCConstants.CancellationTillHubTest.cancelOrderOfMultipleReleasesInPKStatus_ITEM2+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID},supplyTypeOnHand);

		orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
		log.info("OrderID: "+orderID);
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
		orderEntry = omsServiceHelper.getOrderEntry(orderID);
		orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
		orderReleaseId2 = orderEntry.getOrderReleases().get(1).getId().toString();


		createOrderInPKStatusInLMS(orderReleaseId, "PK");
		createOrderInPKStatusInLMS(orderReleaseId2, "PK");
		
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"PK");
		verifyCancelOrderSuccess(orderID);
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"F");
		sft.assertAll();

	}

	//Passed2
	@Test(enabled = true,groups={"regression","cancellationtillHub"}, description = "Cancel Multi Release order in PK state")
	public void cancelOrderOfMultipleReleasesInPK_WP_SHStatus() throws Exception {
		sft=new SoftAssert();
		String skuId[] = { OMSTCConstants.CancellationTillHubTest.cancelOrderOfMultipleReleasesInPKStatus_ITEM1+":2"
				, OMSTCConstants.CancellationTillHubTest.cancelOrderOfMultipleReleasesInPKStatus_ITEM2+":2"
				, OMSTCConstants.CancellationTillHubTest.cancelOrderOfMultipleReleasesInSHStatus_ITEM1+":2" };
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.CancellationTillHubTest.cancelOrderOfMultipleReleasesInPKStatus_ITEM1+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID+":1"
				, OMSTCConstants.CancellationTillHubTest.cancelOrderOfMultipleReleasesInPKStatus_ITEM2+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID+":1",
				OMSTCConstants.CancellationTillHubTest.cancelOrderOfMultipleReleasesInSHStatus_ITEM1+":"+OMSTCConstants.WareHouseIds.warehouseId1_BA+":10000:0:"+vectorSellerID+":1"}, supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.CancellationTillHubTest.cancelOrderOfMultipleReleasesInPKStatus_ITEM1+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID,
				OMSTCConstants.CancellationTillHubTest.cancelOrderOfMultipleReleasesInPKStatus_ITEM2+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID,
				OMSTCConstants.CancellationTillHubTest.cancelOrderOfMultipleReleasesInSHStatus_ITEM1+":"+OMSTCConstants.WareHouseIds.warehouseId1_BA+":10000:0:"+vectorSellerID},supplyTypeOnHand);

		orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
		log.info("OrderID: "+orderID);
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
		orderEntry = omsServiceHelper.getOrderEntry(orderID);
		orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
		orderReleaseId2 = orderEntry.getOrderReleases().get(1).getId().toString();
		orderReleaseId3 = orderEntry.getOrderReleases().get(2).getId().toString();  

		createOrderInPKStatusInLMS(orderReleaseId, "PK");
		orderReleaseStatus = omsServiceHelper.validateReleaseStatusInOMS(orderReleaseId, "PK", 10);
		sft.assertTrue(orderReleaseStatus, "Order Status is not in PK");
		orderReleaseStatus = omsServiceHelper.validateReleaseStatusInOMS(orderReleaseId2, "WP", 10);
		sft.assertTrue(orderReleaseStatus, "Order Status is not in WP");
		omsServiceHelper.updateReleaseStatusDB(orderReleaseId3, "SH");
		orderReleaseStatus = omsServiceHelper.validateReleaseStatusInOMS(orderReleaseId3, "SH", 10);
		sft.assertTrue(orderReleaseStatus, "Order Status is not in SH");

		verifyCancelOrderSuccess(orderID);

		orderReleaseStatus = omsServiceHelper.validateReleaseStatusInOMS(orderReleaseId, "F", 10);
		sft.assertTrue(orderReleaseStatus, "Order Status is not in PK");
		orderReleaseStatus = omsServiceHelper.validateReleaseStatusInOMS(orderReleaseId2, "F", 10);
		sft.assertTrue(orderReleaseStatus, "Order Status is not in WP");
		omsServiceHelper.updateReleaseStatusDB(orderReleaseId3, "SH");
		orderReleaseStatus = omsServiceHelper.validateReleaseStatusInOMS(orderReleaseId3, "SH", 10);
		sft.assertTrue(orderReleaseStatus, "Order Status is not in SH");
		sft.assertAll();

	}



	//Passed2
	@Test(enabled = true,groups={"regression","cancellationtillHub"}, description = "Cancel Release order in PK state")
	public void cancelReleaseOrderInSH_DLStatus() throws Exception {
		sft=new SoftAssert();
		String skuId[] = { OMSTCConstants.CancellationTillHubTest.cancelReleaseOrderInSHStatus_ITEM1+":1" };
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.CancellationTillHubTest.cancelReleaseOrderInSHStatus_ITEM1+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID+":1"}, supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.CancellationTillHubTest.cancelReleaseOrderInSHStatus_ITEM1+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID},
				supplyTypeOnHand);

		orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
		log.info("OrderID: "+orderID);
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
		orderEntry = omsServiceHelper.getOrderEntry(orderID);
		orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();

		omsServiceHelper.updateReleaseStatusDB(orderReleaseId, "SH");
		orderStatus = omsServiceHelper.validateReleaseStatusInOMS(orderReleaseId, "SH", 10);
		sft.assertTrue(orderStatus, "Order Status is not in SH");
		verifyCancelFailure(orderID, "release", 8252, "Cancellation not allowed for release id : "+orderReleaseId);

		omsServiceHelper.updateReleaseStatusDB(orderReleaseId, "DL");
		orderStatus = omsServiceHelper.validateReleaseStatusInOMS(orderReleaseId, "DL", 10);
		sft.assertTrue(orderStatus, "Order Status is not in DL");
		verifyCancelFailure(orderID, "release", 8252, "Cancellation not allowed for release id : "+orderReleaseId);
		sft.assertAll();
	}





	//Passed2
	@Test(enabled = true,groups={"regression","cancellationtillHub"}, description = "Cancel Release order in PK state")
	public void cancelOrderInSH_DLStatus() throws Exception {
		sft=new SoftAssert();
		String skuId[] = { OMSTCConstants.CancellationTillHubTest.cancelOrderInSHStatus_ITEM1+":1" };
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.CancellationTillHubTest.cancelOrderInSHStatus_ITEM1+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID+":1"}, supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.CancellationTillHubTest.cancelOrderInSHStatus_ITEM1+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID},
				supplyTypeOnHand);
		orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
		log.info("OrderID: "+orderID);
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
		orderEntry = omsServiceHelper.getOrderEntry(orderID);
		orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();

		omsServiceHelper.updateReleaseStatusDB(orderReleaseId, "SH");
		orderStatus = omsServiceHelper.validateReleaseStatusInOMS(orderReleaseId, "SH", 10);
		sft.assertTrue(orderStatus, "Order Status is not in SH");
		verifyCancelFailure(orderID, "order", 8053, "Cancellation not allowed because release in not in 'Q', 'RFR', or 'WP' status");

		omsServiceHelper.updateReleaseStatusDB(orderReleaseId, "DL");
		orderStatus = omsServiceHelper.validateReleaseStatusInOMS(orderReleaseId, "DL", 10);
		sft.assertTrue(orderStatus, "Order Status is not in DL");
		verifyCancelFailure(orderID, "order", 8053, "Cancellation not allowed because release in not in 'Q', 'RFR', or 'WP' status");
		sft.assertAll();
	}



	//Passed2
	@Test(enabled = true,groups={"regression","sanity","cancellationtillHub"}, description = "Cancel Multi Release order in PK state")
	public void cancelMultipleReleasesInSHStatus() throws Exception {
		sft=new SoftAssert();
		String skuId[] = { OMSTCConstants.CancellationTillHubTest.cancelMultipleReleasesInSHStatus_ITEM1+":1"
				, OMSTCConstants.CancellationTillHubTest.cancelMultipleReleasesInSHStatus_ITEM2+":1" };
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.CancellationTillHubTest.cancelMultipleReleasesInSHStatus_ITEM1+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID+":1",
				OMSTCConstants.CancellationTillHubTest.cancelMultipleReleasesInSHStatus_ITEM2+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID+":1"}, supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.CancellationTillHubTest.cancelMultipleReleasesInSHStatus_ITEM1+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID,
				OMSTCConstants.CancellationTillHubTest.cancelMultipleReleasesInSHStatus_ITEM2+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID},
				supplyTypeOnHand);

		orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
		log.info("OrderID: "+orderID);
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
		orderEntry = omsServiceHelper.getOrderEntry(orderID);
		orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();

		omsServiceHelper.updateReleaseStatusDB(orderReleaseId, "SH");
		orderStatus = omsServiceHelper.validateReleaseStatusInOMS(orderReleaseId, "SH", 10);
		sft.assertTrue(orderStatus, "Order Status is not in SH");

		verifyCancelFailure(orderID, "release", 8252, "Cancellation not allowed for release id : "+orderReleaseId);
		sft.assertAll();
	}


	//Passed2
	@Test(enabled = true,groups={"regression","cancellationtillHub"}, description = "Cancel Multi Release order in PK state")
	public void cancelOrderOfMultipleReleasesInSHStatus() throws Exception {
		sft=new SoftAssert();
		String skuId[] = { OMSTCConstants.CancellationTillHubTest.cancelOrderOfMultipleReleasesInSHStatus_ITEM1+":1", OMSTCConstants.CancellationTillHubTest.cancelOrderOfMultipleReleasesInSHStatus_ITEM2+":1" };
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.CancellationTillHubTest.cancelOrderOfMultipleReleasesInSHStatus_ITEM1+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID+":1",
				OMSTCConstants.CancellationTillHubTest.cancelOrderOfMultipleReleasesInSHStatus_ITEM2+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID+":1"}, supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.CancellationTillHubTest.cancelOrderOfMultipleReleasesInSHStatus_ITEM1+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID,
				OMSTCConstants.CancellationTillHubTest.cancelOrderOfMultipleReleasesInSHStatus_ITEM2+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID},
				supplyTypeOnHand);
		orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
		log.info("OrderID: "+orderID);
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
		orderEntry = omsServiceHelper.getOrderEntry(orderID);
		orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
		orderReleaseId2 = orderEntry.getOrderReleases().get(1).getId().toString();

		omsServiceHelper.updateReleaseStatusDB(orderReleaseId, "SH");
		orderStatus = omsServiceHelper.validateReleaseStatusInOMS(orderReleaseId, "SH", 10);
		sft.assertTrue(orderStatus, "Order Status is not in SH");
		omsServiceHelper.updateReleaseStatusDB(orderReleaseId2, "SH");
		orderStatus = omsServiceHelper.validateReleaseStatusInOMS(orderReleaseId2, "SH", 10);
		sft.assertTrue(orderStatus, "Order Status is not in SH");

		verifyCancelFailure(orderID, "order", 8053, "Cancellation not allowed because release in not in 'Q', 'RFR', or 'WP' status");
		sft.assertAll();
	}


	//Passed2
	@Test(enabled = true,groups={"regression","cancellationtillHub"}, description = "Cancel Release order in PK state")
	public void cancelLineInPKStatus() throws Exception {
		sft=new SoftAssert();
        String skuId[] = { OMSTCConstants.OtherSkus.skuId_3832+":1" };
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":1000:0:"+vectorSellerID+":1"},supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":1000:0:"+vectorSellerID},supplyTypeOnHand);
		
        orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
        log.info("OrderID: "+orderID);
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        
        createOrderInPKStatusInLMS(orderReleaseId, "PK");
		orderStatusPK = omsServiceHelper.validateReleaseStatusInOMS(orderReleaseId, "PK", 10);
        sft.assertTrue(orderStatusPK, "Order Status is not in PK");
     
        orderLineEntries = omsServiceHelper.getOrderLineEntries(orderReleaseId);
        lineID = ""+orderLineEntries.get(0).getId();
        responseAfterCancelLine = omsServiceHelper.cancelLine(orderReleaseId, login, new String[] {lineID +":1"}, 41);
        
        sft.assertEquals(responseAfterCancelLine.getStatus().getStatusCode(), 8076);
        sft.assertEquals(responseAfterCancelLine.getStatus().getStatusType(), Type.ERROR);
		//isCancelAssertFailure(8076, "Cancellation not allowed because release in not in 'Q', 'RFR', or 'WP' status", "release");
        sft.assertAll();
	}

	//Passed2
	@Test(enabled = true,groups={"regression","cancellationtillHub"}, description = "Cancel Release order in PK state")
	public void cancelLineInSHStatus() throws Exception {
		sft=new SoftAssert();
        String skuId[] = { OMSTCConstants.OtherSkus.skuId_3832+":1" };
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":1000:0:"+vectorSellerID+":1"},supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":1000:0:"+vectorSellerID},supplyTypeOnHand);
		
        orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
        log.info("OrderID: "+orderID);
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        
        omsServiceHelper.updateReleaseStatusDB(orderReleaseId, "SH");
        orderStatusPK = omsServiceHelper.validateReleaseStatusInOMS(orderReleaseId, "SH", 10);
        sft.assertTrue(orderStatusPK, "Order Status is not in SH");
     
        orderLineEntries = omsServiceHelper.getOrderLineEntries(orderReleaseId);
        lineID = ""+orderLineEntries.get(0).getId();
        omsServiceHelper.updateOrderLineStatusDB(lineID, "QD");
        responseAfterCancelLine = omsServiceHelper.cancelLine(orderReleaseId, login, new String[] {lineID +":1"}, 41);
        
        sft.assertEquals(responseAfterCancelLine.getStatus().getStatusCode(), 8076);
        sft.assertEquals(responseAfterCancelLine.getStatus().getStatusType(), Type.ERROR);
        sft.assertAll();
	}

	//Passed2
	@Test(enabled = true,groups={"regression","cancellationtillHub"}, description = "Check for isCancellable for a orderRelease in PK state")
	public void checkIsCancellableOrderReleasePK() throws Exception {
		sft=new SoftAssert();
		String skuId[] = { OMSTCConstants.OtherSkus.skuId_3832+":1" };
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID+":1"},supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID},supplyTypeOnHand);

		orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
		log.info("OrderID: "+orderID);
		orderEntry = omsServiceHelper.getOrderEntry(orderID);
        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
        
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");

		createOrderInPKStatusInLMS(orderReleaseId, "PK");
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"PK");

		//Check if isCancellable is allowed 
		cancellationRes =   omsServiceHelper.isCancellationAllowed(orderReleaseId);
		isCancelAssertSuccess(1046, "Cancellation allowed", "release");
		sft.assertAll();
	}

	//Passed2
	@Test(enabled = true,groups={"regression","cancellationtillHub"}, description = "Check for isCancellable for a MultiorderRelease in PK state")
	public void checkIsCancellableOrderMultiReleasePK() throws Exception {
		sft=new SoftAssert();
		String skuId[] = { OMSTCConstants.OtherSkus.skuId_3832+":1",OMSTCConstants.OtherSkus.skuId_3837+":1" };

		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":1000:0:"+vectorSellerID+":1",OMSTCConstants.OtherSkus.skuId_3837+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1"},supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":1000:0:"+vectorSellerID,
				OMSTCConstants.OtherSkus.skuId_3837+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID},supplyTypeOnHand);
		orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
		log.info("OrderID: "+orderID);

		omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");

		orderEntry = omsServiceHelper.getOrderEntry(orderID);
		orderReleaseEntries = orderEntry.getOrderReleases();
		orderReleaseID1 = orderReleaseEntries.get(0).getId().toString();
		orderReleaseID2 = orderReleaseEntries.get(1).getId().toString();
		
		createOrderInPKStatusInLMS(orderReleaseID1, "PK");
		createOrderInPKStatusInLMS(orderReleaseID2, "PK");
		
		//Check if isCancellable is allowed 
		cancellationRes =   omsServiceHelper.isCancellationAllowed(orderReleaseID1);
		//Validation
		isCancelAssertSuccess(1046, "Cancellation allowed", "release");

		cancellationRes =   omsServiceHelper.isCancellationAllowed(orderReleaseID2);
		//Validation
		isCancelAssertSuccess(1046, "Cancellation allowed", "release");
		sft.assertAll();
	}

	//Passed2
	@Test(enabled = true,groups={"regression","cancellationtillHub"}, description = "Check for isCancellable for a orderRelease in SH state")
	public void checkIsCancellableOrderReleaseSH() throws Exception {
		sft=new SoftAssert();
		String skuId[] = { OMSTCConstants.OtherSkus.skuId_3832+":1" };
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID+":1"},supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID},supplyTypeOnHand);
		
		orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
		log.info("OrderID: "+orderID);

		omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");

		orderEntry = omsServiceHelper.getOrderEntry(orderID);
		orderReleaseEntries = orderEntry.getOrderReleases();
		orderReleaseID1 = orderReleaseEntries.get(0).getId().toString();

		releaseStatus1 = omsServiceHelper.updateReleaseStatusDB(orderReleaseID1, "SH");
		sft.assertEquals(releaseStatus1, "SH",orderReleaseID1+" Should be in SH status");

		//Check if isCancellable is allowed 
		cancellationRes =   omsServiceHelper.isCancellationAllowed(orderReleaseID1);
		isCancelAssertFailure(8252, "Cancellation not allowed for release id : "+orderReleaseID1, "release");
		sft.assertAll();

	}

	//Passed2
	@Test(enabled = true,groups={"regression","cancellationtillHub"}, description = "Check for isCancellable for a MultiorderRelease in SH state")
	public void checkIsCancellableOrderMultiReleaseSH() throws Exception {
		sft=new SoftAssert();
		String skuId[] = { OMSTCConstants.OtherSkus.skuId_3832+":1",OMSTCConstants.OtherSkus.skuId_3837+":1" };
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":1000:0:"+vectorSellerID+":1",OMSTCConstants.OtherSkus.skuId_3837+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1"},supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":1000:0:"+vectorSellerID,
				OMSTCConstants.OtherSkus.skuId_3837+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID},supplyTypeOnHand);
		orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
		log.info("OrderID: "+orderID);
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");

		orderEntry = omsServiceHelper.getOrderEntry(orderID);
		orderReleaseEntries = orderEntry.getOrderReleases();
		orderReleaseID1 = orderReleaseEntries.get(0).getId().toString();
		orderReleaseID2 = orderReleaseEntries.get(1).getId().toString();

		releaseStatus1 = omsServiceHelper.updateReleaseStatusDB(orderReleaseID1, "SH");
		sft.assertEquals(releaseStatus1, "SH",orderReleaseID1+" Should be in SH status");

		releaseStatus2 = omsServiceHelper.updateReleaseStatusDB(orderReleaseID2, "SH");
		sft.assertEquals(releaseStatus2, "SH",orderReleaseID2+" Should be in SH status");

		//Check if isCancellable is allowed 
		cancellationRes =   omsServiceHelper.isCancellationAllowed(orderReleaseID1);
		//Validation
		isCancelAssertFailure(8252, "Cancellation not allowed for release id : "+orderReleaseID1, "release");

		cancellationRes =   omsServiceHelper.isCancellationAllowed(orderReleaseID2);
		//Validation
		isCancelAssertFailure(8252, "Cancellation not allowed for release id : "+orderReleaseID2, "release");
		sft.assertAll();
		
	}

	//Passed2
	@Test(enabled=true,groups = {"regression","cancellationtillHub"}, dataProvider = "checkIsCancellableOrderQOnHold39_6_7_9_24_25_26_28StatusDP", dataProviderClass = CancellationTillHubDP.class, description = "Check Is Cancellable in Q On Hold39 Status")
	public void checkIsCancellableOrderQOnHold39_6_7_9_24_25_26_28Status(OrderResponse orderResponse, String login, HashMap<String, Object> orderEntryMap, String cartContext, String paymentStatus,boolean putOnHold,long holdReasonId) throws IOException, JAXBException, ManagerException {
		sft=new SoftAssert();
		sft.assertEquals(orderResponse.getStatus().getStatusType(), StatusResponse.Type.SUCCESS,"Issue while creating order");
        String storeOrderId = orderResponse.getData().get(0).getStoreOrderId();
        HashMap<String,Object> hashMap = (HashMap<String, Object>) omsServiceHelper.getOrderEntryDB(storeOrderId).get(0);
        String orderID =   hashMap.get("id").toString();

        sft.assertEquals(bountyServiceHelper.getxCartOrderTableDBEntry(storeOrderId).get("status"),"PV");
        OrderCreationResponse orderQueueResponse = bountyServiceHelper.queueOrder(storeOrderId, ""+orderEntryMap.get("xid"), login, "DEFAULT", "SUCCESS", true);
        sft.assertEquals(orderQueueResponse.getStatus().getStatusType(), StatusResponse.Type.SUCCESS);
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"Q");
        omsServiceHelper.updateOrderHoldReasonIdDB(orderID, holdReasonId);

		orderEntry = omsServiceHelper.getOrderEntry(orderID);
		log.info("OrderHoldStatus "+orderEntry.getOnHold()+" "+orderEntry.getOnHoldReasonId());
		sft.assertTrue(orderEntry.getOnHold(), "Order is not in Hold status");
		assertEquals(""+orderEntry.getOnHoldReasonId(), ""+holdReasonId);

		orderReleaseEntries = orderEntry.getOrderReleases();
		orderReleaseID1 = orderReleaseEntries.get(0).getId().toString();
		      
		//Check if isCancellable is allowed 
		cancellationRes =   omsServiceHelper.isCancellationAllowed(orderReleaseID1);
		//Validation
		isCancelAssertFailure(8252, "Cancellation not allowed for release id : "+orderReleaseID1, "release");
		errorMessage = "Cancellation not allowed for release id : "+orderReleaseID1;
		verifyCancelFailure(orderID, "release",8252,errorMessage);
		sft.assertAll();
	}

	//Passed2
	@Test(enabled=true,groups = {"regression","cancellationtillHub"}, dataProvider = "checkIsCancellableOrderInQStatusDP", dataProviderClass = CancellationTillHubDP.class, description = "Check Is Cancellable in Q On Hold39 Status")
	public void checkIsCancellableOrderInQStatusWithOutHold(OrderResponse orderResponse, String login, HashMap<String, Object> orderEntryMap, String cartContext, String paymentStatus,boolean putOnHold) throws IOException, JAXBException, ManagerException {
		sft=new SoftAssert();
		sft.assertEquals(orderResponse.getStatus().getStatusType(), StatusResponse.Type.SUCCESS,"Issue while creating order");

		String storeOrderId = orderResponse.getData().get(0).getStoreOrderId();
        HashMap<String,Object> hashMap = (HashMap<String, Object>) omsServiceHelper.getOrderEntryDB(storeOrderId).get(0);
        String orderID =  hashMap.get("id").toString();

        sft.assertEquals(bountyServiceHelper.getxCartOrderTableDBEntry(storeOrderId).get("status"),"PV");
        OrderCreationResponse orderQueueResponse = bountyServiceHelper.queueOrder(storeOrderId, ""+orderEntryMap.get("xid"), login, "DEFAULT", "SUCCESS", false);
        sft.assertEquals(orderQueueResponse.getStatus().getStatusType(), StatusResponse.Type.SUCCESS);


		omsServiceHelper.checkReleaseStatusForOrder(orderID,"Q");
		orderEntry = omsServiceHelper.getOrderEntry(orderID);
		orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
		orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(orderReleaseId);
		DBUtilities.exUpdateQuery(
				"UPDATE order_release SET is_on_hold=0 where id in ("+orderReleaseId+");", "myntra_oms");
		End2EndHelper.sleep(5000L);
		orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(orderReleaseId);
		//      log.info("OrderHoldStatus "+orderEntry.getOnHold()+" "+orderEntry.getOnHoldReasonId());
		sft.assertEquals(orderReleaseEntry.getOnHold(),Boolean.FALSE, "OrderRelease is in Hold status");

		//Check if isCancellable is allowed 
		cancellationRes =   omsServiceHelper.isCancellationAllowed(orderReleaseId);
		//Validation
		isCancelAssertSuccess(1046, "Cancellation allowed", "release");
		//Cancel Order
		verifyCancelOrderSuccess(orderID);
		sft.assertAll();
	}

	//Passed2
	@Test(enabled=true,groups = {"regression","cancellationtillHub"}, description = "OrderQueueOnHold5 From PP Status")
	public void checkIsCancellableOrderInPPStatus() throws IOException, JAXBException, ManagerException {
		sft=new SoftAssert();
		String skuId1[] = {OMSTCConstants.OtherSkus.skuId_3832+":1"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":1000:0:"+vectorSellerID+":1"},supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":1000:0:"+vectorSellerID},supplyTypeOnHand);
		
		orderEntry1 = bountyServiceHelper.getCreateOrderEntry(login, password, skuId1, "ADD","", "200", "50", "");
		orderResponse1 = bountyServiceHelper.createOrder((CartEntry) orderEntry1.get("cart"), "12345", login, ""+orderEntry1.get("xid"), "0", "0", "0.0", OMSTCConstants.Pincodes.PINCODE_560068,
				"responsive", "DEFAULT", "on");

		sft.assertEquals(orderResponse1.getStatus().getStatusType(), StatusResponse.Type.SUCCESS,"Issue while creating order");

		orderID = omsServiceHelper.getOrderId(orderResponse1.getData().get(0).getStoreOrderId());        
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"PP");

		orderEntry = omsServiceHelper.getOrderEntry(orderID);
		//    log.info("OrderHoldStatus "+orderEntry.getOnHold()+" "+orderEntry.getOnHoldReasonId());
		sft.assertEquals(orderEntry.getOnHold(),Boolean.FALSE, "Order is in Hold status");

		orderEntry = omsServiceHelper.getOrderEntry(orderID);
		orderReleaseEntries = orderEntry.getOrderReleases();
		orderReleaseID1 = orderReleaseEntries.get(0).getId().toString();

		//Check if isCancellable is allowed 
		cancellationRes =   omsServiceHelper.isCancellationAllowed(orderReleaseID1);
		//Validation
		isCancelAssertFailure(8252, "Cancellation not allowed for release id : "+orderReleaseID1,"release");

		errorMessage = "Cancellation not allowed because release in not in 'Q', 'RFR', or 'WP' status";
		verifyCancelFailure(orderID, "order",8053,errorMessage);
		errorMessage = "Cancellation not allowed for release id : "+orderReleaseID1;
		verifyCancelFailure(orderID, "release",8252,errorMessage);
		sft.assertAll();
	}

	//Passed2
	@Test(enabled=true,groups = {"regression","cancellationtillHub"}, description = "OrderQueueOnHold5 From PP Status")
	public void checkIsCancellableOrderInPVStatus() throws IOException, JAXBException, ManagerException {
		sft=new SoftAssert();
		String skuId1[] = {OMSTCConstants.OtherSkus.skuId_3832+":1"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":1000:0:"+vectorSellerID+":1"},supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":1000:0:"+vectorSellerID},supplyTypeOnHand);
		
		orderEntry1 = bountyServiceHelper.getCreateOrderEntry(login, password, skuId1, "ADD","", "200", "50", "");
		orderResponse1 = bountyServiceHelper.createOrder((CartEntry) orderEntry1.get("cart"), "12345", login, ""+orderEntry1.get("xid"), "0", "0", "0.0", OMSTCConstants.Pincodes.PINCODE_560068,
				"responsive", "DEFAULT", "cod");
		sft.assertEquals(orderResponse1.getStatus().getStatusType(), StatusResponse.Type.SUCCESS,"Issue while creating order");
		orderID = omsServiceHelper.getOrderId(orderResponse1.getData().get(0).getStoreOrderId());        

		omsServiceHelper.checkReleaseStatusForOrder(orderID,"PV");

		orderEntry = omsServiceHelper.getOrderEntry(orderID);
		//    log.info("OrderHoldStatus "+orderEntry.getOnHold()+" "+orderEntry.getOnHoldReasonId());
		sft.assertEquals(orderEntry.getOnHold(),Boolean.FALSE, "Order is in Hold status");

		orderEntry = omsServiceHelper.getOrderEntry(orderID);
		orderReleaseEntries = orderEntry.getOrderReleases();
		orderReleaseID1 = orderReleaseEntries.get(0).getId().toString();

		//Check if isCancellable is allowed 
		cancellationRes =   omsServiceHelper.isCancellationAllowed(orderReleaseID1);
		//Validation
		isCancelAssertFailure(8252, "Cancellation not allowed for release id : "+orderReleaseID1,"release");

		errorMessage = "Cancellation not allowed because release in not in 'Q', 'RFR', or 'WP' status";
		verifyCancelFailure(orderID, "order",8053,errorMessage);
		errorMessage = "Cancellation not allowed for release id : "+orderReleaseID1;
		verifyCancelFailure(orderID, "release",8252,errorMessage);
		sft.assertAll();
	}

	//Passed2
	@Test(enabled=true,groups = {"regression","cancellationtillHub"},dataProvider="checkIsCancellableOrderInWPStatusWithOnholdReason6_7_9_24_25_26_28_39DP", dataProviderClass=CancellationTillHubDP.class, description = "check if is cancellation allowed for WP status")
	public void checkIsCancellableOrderInWPStatusWithOnholdReason6_7_9_24_25_26_28_39(String orderID,String login,long holdReasonId) throws Exception {
		sft=new SoftAssert();
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        omsServiceHelper.updateOrderHoldReasonIdDB(orderID, holdReasonId);
        orderReleaseEntries = orderEntry.getOrderReleases();
        orderReleaseID1 = orderReleaseEntries.get(0).getId().toString();
        
		// log.info("OrderHoldStatus "+orderEntry.getOnHold()+" "+orderEntry.getOnHoldReasonId());
		orderEntry = omsServiceHelper.getOrderEntry(orderID);
		sft.assertEquals(orderEntry.getOnHold(),Boolean.TRUE, "Order is not in OnHold status");
		sft.assertNotEquals(""+orderEntry.getOnHoldReasonId(),holdReasonId, "Order is not in Hold status with reasonId: "+holdReasonId);

		//Check if isCancellable is allowed 
		cancellationRes =   omsServiceHelper.isCancellationAllowed(orderReleaseID1);
		//Validation
		isCancelAssertFailure(8252, "Cancellation not allowed for release id : "+orderReleaseID1,"release");
		errorMessage = "Cancellation not allowed because order is on hold with reason code "+holdReasonId;
		verifyCancelFailure(orderID, "order",8053,errorMessage);
		errorMessage = "Cancellation not allowed for release id : "+orderReleaseID1;
		verifyCancelFailure(orderID, "release",8252,errorMessage);
		sft.assertAll();
	}

	//Passed2
	@Test(enabled=true,groups = {"regression","cancellationtillHub"},dataProvider="checkIsCancellableOrderInRFRStatusWithOnholdReason6_7_9_24_25_26_28_39DP", dataProviderClass=CancellationTillHubDP.class, description = "check if is cancellation allowed for WP status")
	public void checkIsCancellableOrderInRFRStatusWithOnholdReason6_7_9_24_25_26_28_39(String orderID,String login,long holdReasonId) throws Exception {
		sft=new SoftAssert();
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        omsServiceHelper.updateOrderHoldReasonIdDB(orderID, holdReasonId);
        orderReleaseEntries = orderEntry.getOrderReleases();
        orderReleaseID1 = orderReleaseEntries.get(0).getId().toString();
        releaseStatus1 = omsServiceHelper.updateReleaseStatusDB(orderReleaseID1, "RFR");
        sft.assertEquals(releaseStatus1, "RFR",orderReleaseID1+" Should be in RFR status");
        
		// log.info("OrderHoldStatus "+orderEntry.getOnHold()+" "+orderEntry.getOnHoldReasonId());
		orderEntry = omsServiceHelper.getOrderEntry(orderID);
		sft.assertEquals(orderEntry.getOnHold(),Boolean.TRUE, "Order is not in OnHold status");
		sft.assertNotEquals(""+orderEntry.getOnHoldReasonId(),holdReasonId, "Order is not in Hold status with reasonId: "+holdReasonId);

		//Check if isCancellable is allowed 
		cancellationRes =   omsServiceHelper.isCancellationAllowed(orderReleaseID1);
		//Validation
		isCancelAssertFailure(8252, "Cancellation not allowed for release id : "+orderReleaseID1,"release");
		errorMessage = "Cancellation not allowed because order is on hold with reason code "+holdReasonId;
		verifyCancelFailure(orderID, "order",8053,errorMessage);
		errorMessage = "Cancellation not allowed for release id : "+orderReleaseID1;
		verifyCancelFailure(orderID, "release",8252,errorMessage);
		sft.assertAll();
	}


	//Passed2
	@Test(enabled=true,groups = {"regression","cancellationtillHub"}, dataProvider = "checkIsCancellableOrderInDL_C_L_RTOStatusDP", dataProviderClass = CancellationTillHubDP.class, description = "Check Is Cancellable in Q On Hold39 Status")
	public void checkIsCancellableOrderInDL_C_L_RTOStatus(String orderID, String login,String statusCode) throws IOException, JAXBException, ManagerException {
		sft=new SoftAssert();
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        orderReleaseEntries = orderEntry.getOrderReleases();
        orderReleaseID1 = orderReleaseEntries.get(0).getId().toString();
        releaseStatus1 = omsServiceHelper.updateReleaseStatusDB(orderReleaseID1, statusCode);
        sft.assertEquals(releaseStatus1, statusCode,orderReleaseID1+" Should be in "+statusCode+" status");
        
		//Check if isCancellable is allowed 
		cancellationRes =   omsServiceHelper.isCancellationAllowed(orderReleaseID1);
		//Validation

		isCancelAssertFailure(8252, "Cancellation not allowed for release id : "+orderReleaseID1,"release");
		errorMessage = "Cancellation not allowed because release in not in 'Q', 'RFR', or 'WP' status";
		verifyCancelFailure(orderID, "order",8053,errorMessage);
		errorMessage = "Cancellation not allowed for release id : "+orderReleaseID1;
		verifyCancelFailure(orderID, "release",8252,errorMessage);
		sft.assertAll();

	}
	//Passed2
	@Test(enabled=true,groups = {"regression","cancellationtillHub"}, dataProvider = "checkIsCancellableOrderInPKStatusDP", dataProviderClass = CancellationTillHubDP.class, description = "Check Is Cancellable in Q On Hold39 Status")
	public void checkIsCancellableOrderInPKStatus(String releaseId, String lmsStatus,String lmsShipmentStatus,String status) throws IOException, JAXBException {
		sft=new SoftAssert();
		
		createOrderInPKStatusInLMS(releaseId, "PK");
		updateReleaseStatusInLMS(releaseId, lmsStatus, lmsShipmentStatus);
		omsServiceHelper.validateReleaseStatusInOMS(releaseId, "PK", 10);
		//Check if isCancellable is allowed 
		cancellationRes =   omsServiceHelper.isCancellationAllowed(releaseId);
				
		//Validation
		if(status.equalsIgnoreCase("error")){
			isCancelAssertFailure(8252, "Cancellation not allowed for release id : "+releaseId,"release");
		}else if(status.equalsIgnoreCase("success")){
			isCancelAssertSuccess(1046, "Cancellation allowed","release");
		}
		sft.assertAll();

	}

	//Passed2
	@Test(enabled=true,groups = {"regression","cancellationtillHub"}, dataProvider = "checkIsCancellableOrderInPKStatusForWarehouseNotAllowingDP", dataProviderClass = CancellationTillHubDP.class, description = "Check Is Cancellable in Q On Hold39 Status")
	public void checkIsCancellableOrderInPKStatusForWhichWarehouseNotAllowing(String releaseId, String lmsStatus,String lmsShipmentStatus,String status) throws IOException, JAXBException {
		sft=new SoftAssert();
		//Check if isCancellable is allowed
		createOrderInPKStatusInLMS(releaseId, "PK");
		updateReleaseStatusInLMS(orderReleaseId, lmsStatus, lmsShipmentStatus);
		omsServiceHelper.validateReleaseStatusInOMS(releaseId, "PK", 10);
		
		cancellationRes =   omsServiceHelper.isCancellationAllowed(releaseId);
		//Validation
		if(status.equalsIgnoreCase("error")){
			isCancelAssertFailure(8252, "Cancellation not allowed for release id : "+releaseId,"release");
		}else if(status.equalsIgnoreCase("success")){
			isCancelAssertSuccess(1046, "Cancellation allowed","release");
		}
		sft.assertAll();

	}

	//Passed2
	@Test(enabled = true,groups={"regression","cancellationtillHub"}, description = "Cancel Release order in PK state")
	public void recieveShipmentAfterRTO() throws Exception {
		sft=new SoftAssert();
		String skuId[] = { OMSTCConstants.CancellationTillHubTest.cancelReleaseOrderInPKStatus_ITEM1+":1" };
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.CancellationTillHubTest.cancelReleaseOrderInPKStatus_ITEM1+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID+":1"}, supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.CancellationTillHubTest.cancelReleaseOrderInPKStatus_ITEM1+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID},
				supplyTypeOnHand);

		//  login = "end2endautomation4@gmail.com"; password="myntra@123";
		orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
		log.info("OrderID: "+orderID);
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
		orderEntry = omsServiceHelper.getOrderEntry(orderID);
		orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
		orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(orderReleaseId);

		End2EndHelper.sleep(10000L);
		List<ReleaseEntry> releaseEntries = new ArrayList<>();
		releaseEntries.add(new ReleaseEntry.Builder(orderReleaseId, ReleaseStatus.RTO).build());
		ReleaseEntryList releaseEntryList= new ReleaseEntryList(releaseEntries);
		processRelease.processReleaseToStatus(releaseEntryList);

		orderStatusPK = omsServiceHelper.validateReleaseStatusInOMS(orderReleaseId, "RTO", 10);
		sft.assertTrue(orderStatusPK, "Order Status is not in RTO");
		//Verify status in LMS
		verifyStatusInLMS(orderReleaseId,"RTO");
		
		RtoResponse response = rmsServiceHelper.recieveShipmentInRejoy(orderReleaseId, 36);
		//  Assert.assertTrue(response.getStatus().getStatusCode(),, "Order Status is not in RTO");
		sft.assertEquals(response.getStatus().getStatusType(),Type.SUCCESS, "Order Status is not in RTO");

		verifyStatusInLMS(orderReleaseId,"RTO_R");
		//Validate item status
		validateItemStatusInWMS(OMSTCConstants.CancellationTillHubTest.cancelReleaseOrderInPKStatus_ITEM1, orderReleaseId, "ACCEPTED_RETURNS");
		//Restock Item in RMS
		HashMap<String, Object> itemDetailInWMS = (HashMap<String, Object>) 
				DBUtilities.exSelectQueryForSingleRecord("select * from item where order_id ="+orderReleaseId, "wms");
		String barcode = (String) itemDetailInWMS.get("barcode");
		
		response = rmsServiceHelper.reStockItemInRejoy(barcode, 36);
		sft.assertEquals(response.getStatus().getStatusType(),Type.SUCCESS, "Restock didn't happen");
		itemDetailInWMS = (HashMap<String, Object>) 
				DBUtilities.exSelectQueryForSingleRecord("select * from item where barcode ="+barcode, "wms");
		String itemStatus = (String) itemDetailInWMS.get("item_status");
		sft.assertEquals("CUSTOMER_RETURNED", itemStatus,"Item status is not CUSTOMER_RETURNED after RTO recieve");
		sft.assertAll();
	}
	
	//Passed2
	@Test(enabled = true,groups={"regression","cancellationtillHub"}, description = "Cancel Release order in PK state")
	public void recieveShipmentAfterCancel() throws Exception {
		sft=new SoftAssert();
		String skuId[] = { OMSTCConstants.CancellationTillHubTest.cancelReleaseOrderInPKStatus_ITEM1+":1" };
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.CancellationTillHubTest.cancelReleaseOrderInPKStatus_ITEM1+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID+":1"}, supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.CancellationTillHubTest.cancelReleaseOrderInPKStatus_ITEM1+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID},
				supplyTypeOnHand);

		//  login = "end2endautomation4@gmail.com"; password="myntra@123";
		orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
		log.info("OrderID: "+orderID);
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
		orderEntry = omsServiceHelper.getOrderEntry(orderID);
		orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
		orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(orderReleaseId);

		End2EndHelper.sleep(10000L);

		List<ReleaseEntry> releaseEntries = new ArrayList<>();
		releaseEntries.add(new ReleaseEntry.Builder(orderReleaseId, ReleaseStatus.PK).build());
		ReleaseEntryList releaseEntryList= new ReleaseEntryList(releaseEntries);
		processRelease.processReleaseToStatus(releaseEntryList);

		orderStatusPK = omsServiceHelper.validateReleaseStatusInOMS(orderReleaseId, "PK", 10);
		sft.assertTrue(orderStatusPK, "Order Status is not in PK");
		//Cancel
		verifyCancelReleaseSuccess(orderReleaseId);
		//Verify status in LMS
		verifyStatusInLMS(orderReleaseId,"CANCELLED_IN_HUB");

		RtoResponse response = rmsServiceHelper.recieveShipmentInRejoy(orderReleaseId, 36);
		//  Assert.assertTrue(response.getStatus().getStatusCode(),, "Order Status is not in RTO");
		sft.assertEquals(response.getStatus().getStatusType(),Type.SUCCESS, "Response "
				+ "is "+response.getStatus().getStatusMessage());

		verifyStatusInLMS(orderReleaseId,"RCVD_IN_DISPATCH_HUB");
		//Validate item status
		validateItemStatusInWMS(OMSTCConstants.CancellationTillHubTest.cancelReleaseOrderInPKStatus_ITEM1, orderReleaseId, "ACCEPTED_RETURNS");
		//Restock Item in RMS
		HashMap<String, Object> itemDetailInWMS = (HashMap<String, Object>) 
				DBUtilities.exSelectQueryForSingleRecord("select * from item where order_id ="+orderReleaseId, "wms");
		String barcode = (String) itemDetailInWMS.get("barcode");
		
		response = rmsServiceHelper.reStockItemInRejoy(barcode, 36);
		sft.assertEquals(response.getStatus().getStatusType(),Type.SUCCESS, "Restock didn't happen");
		itemDetailInWMS = (HashMap<String, Object>) 
				DBUtilities.exSelectQueryForSingleRecord("select * from item where barcode ="+barcode, "wms");
		String itemStatus = (String) itemDetailInWMS.get("item_status");
		sft.assertEquals("CUSTOMER_RETURNED", itemStatus,"Item status is not CUSTOMER_RETURNED after RTO recieve");
		sft.assertAll();
	}
	
	@Test(enabled = true,groups={"regression","cancellationtillHub"}, description = "Cancel Multi Release order in PK state")
	public void cancelOneLineInWPAndOrderInPKStatus() throws Exception {
		sft=new SoftAssert();
		String skuId[] = { OMSTCConstants.CancellationTillHubTest.cancelMultipleReleasesInPKStatus_ITEM1+":1",
				OMSTCConstants.CancellationTillHubTest.cancelMultipleReleasesInPKStatus_ITEM2+":1" };
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.CancellationTillHubTest.cancelMultipleReleasesInPKStatus_ITEM1+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID+":1"
				, OMSTCConstants.CancellationTillHubTest.cancelMultipleReleasesInPKStatus_ITEM2+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID+":1"}, supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.CancellationTillHubTest.cancelMultipleReleasesInPKStatus_ITEM1+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID,
				OMSTCConstants.CancellationTillHubTest.cancelMultipleReleasesInPKStatus_ITEM2+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID},supplyTypeOnHand);

		orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
		log.info("OrderID: "+orderID);
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
		orderEntry = omsServiceHelper.getOrderEntry(orderID);
		orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString(); 
		//Cancel one Line
		lineID = omsServiceHelper.getOrderLineEntries(orderReleaseId).get(0).getId().toString();
		OrderReleaseResponse cancellationRes = omsServiceHelper.cancelLine
				(orderReleaseId, login, new String[] {lineID +":"+OMSTCConstants.WareHouseIds.warehouseId1_BA+""}, 41);
        assertEquals(cancellationRes.getStatus().getStatusCode(), 1034,"Status code is not correct");
        assertEquals(cancellationRes.getStatus().getStatusType(), Type.SUCCESS,"Error in response");
        
        createOrderInPKStatusInLMS(orderReleaseId, "PK");
		orderStatusPK = omsServiceHelper.validateReleaseStatusInOMS(orderReleaseId, "PK", 10);
		sft.assertTrue(orderStatusPK, "Order Status is not in PK");

		verifyCancelReleaseSuccess(orderReleaseId);
		sft.assertAll();
	}
	
	//Passed2
		@Test(enabled = true,groups={"regression","cancellationtillHub"}, description = "Cancel Release order in PK state")
		public void exchangeCancelInPKStatus() throws Exception {
			sft=new SoftAssert();
			String skuId[] = { OMSTCConstants.CancellationTillHubTest.cancelReleaseOrderInPKStatus_ITEM1+":1" };
			atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.CancellationTillHubTest.cancelReleaseOrderInPKStatus_ITEM1+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID+":1"}, supplyTypeOnHand);
			imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.CancellationTillHubTest.cancelReleaseOrderInPKStatus_ITEM1+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID},
					supplyTypeOnHand );

			//  login = "end2endautomation4@gmail.com"; password="myntra@123";
			orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
			log.info("OrderID: "+orderID);
			omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
			orderEntry = omsServiceHelper.getOrderEntry(orderID);
			orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
			orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(orderReleaseId);

			End2EndHelper.sleep(10000L);

			List<ReleaseEntry> releaseEntries = new ArrayList<>();
			releaseEntries.add(new ReleaseEntry.Builder(orderReleaseId, ReleaseStatus.DL).build());
			ReleaseEntryList releaseEntryList= new ReleaseEntryList(releaseEntries);
			processRelease.processReleaseToStatus(releaseEntryList);

			orderStatusPK = omsServiceHelper.validateReleaseStatusInOMS(orderReleaseId, "DL", 10);
			sft.assertTrue(orderStatusPK, "Order Status is not in DL");
			
			//Create Exchange
	        OrderLineEntry orderLineEntry = omsServiceHelper.getOrderLineEntries(orderReleaseId).get(0);
	        String lineID = ""+orderLineEntry.getId();
	        exchangeOrderResponse =(ExchangeOrderResponse) 
	        		ppsServiceHelper.createExchange(""+orderID, lineID, "DNL", 1, OMSTCConstants.CancellationTillHubTest.cancelReleaseOrderInPKStatus_ITEM1);
	        sft.assertTrue(exchangeOrderResponse.getSuccess());
	        sft.assertNotNull(exchangeOrderResponse.getExchangeOrderId());
	        sft.assertNotEquals(exchangeOrderResponse.getExchangeOrderId(), orderID);
	        String exchangeStoreOrderId = exchangeOrderResponse.getExchangeOrderId();
			String exchangeReleaseId = omsServiceHelper.getOrderEntry(exchangeStoreOrderId).getOrderReleases().iterator().next().getId().toString();
	        //mark order PK exchange order
			List<ReleaseEntry> releaseEntries_Exe = new ArrayList<>();
			releaseEntries.add(new ReleaseEntry.Builder(exchangeReleaseId, ReleaseStatus.PK).build());
			ReleaseEntryList releaseEntryList_Exe= new ReleaseEntryList(releaseEntries_Exe);
			processRelease.processReleaseToStatus(releaseEntryList_Exe);
			orderStatusPK = omsServiceHelper.validateReleaseStatusInOMS(exchangeReleaseId, "PK", 10);
			sft.assertTrue(orderStatusPK, "Order Status is not in PK");
			//Cancel exchange
			verifyCancelReleaseSuccess(exchangeReleaseId);
			//Verify status in LMS
			verifyStatusInLMS(exchangeReleaseId,"CANCELLED_IN_HUB");

			RtoResponse response = rmsServiceHelper.recieveShipmentInRejoy(exchangeReleaseId, 36);
			//  Assert.assertTrue(response.getStatus().getStatusCode(),, "Order Status is not in RTO");
			sft.assertEquals(response.getStatus().getStatusType(),Type.SUCCESS, "Order Status is not in RTO");

			verifyStatusInLMS(exchangeReleaseId,"RCVD_IN_DISPATCH_HUB");
			
					//Validate item status
			validateItemStatusInWMS(OMSTCConstants.CancellationTillHubTest.cancelReleaseOrderInPKStatus_ITEM1, exchangeReleaseId, "ACCEPTED_RETURNS");
			sft.assertAll();
		}

	
     *//**
     * This function is to validate status in LMS
     * @param orderReleaseId
     * @param status
     *//*
    public void verifyStatusInLMS(String orderReleaseId,String status){
			HashMap<String, Object> orderToShip = (HashMap<String, Object>) 
					DBUtilities.exSelectQueryForSingleRecord("select * from order_to_ship where order_id = "+orderReleaseId, "lms");
			lmsStatus = (String) orderToShip.get("status");
			sft.assertEquals(lmsStatus, status,"Release is not in "+status+" status");
      }
	
	
	*//**
	 * This function is to validate item status after RTO Receive
	 * @param skuId
	 * @param orderID
	 * @param expectedStatus
	 *//*
	public void validateItemStatusInWMS(String skuId,String orderID,String expectedStatus){
		HashMap<String, Object> itemDetailInWMS = (HashMap<String, Object>) 
				DBUtilities.exSelectQueryForSingleRecord("select * from item where order_id ="+orderID+" and sku_id='"+skuId+"'", "wms");
		String itemStatus = (String) itemDetailInWMS.get("item_status");
		sft.assertEquals(expectedStatus, itemStatus,"Item status is not "+expectedStatus+" after RTO recieve");
	}

}
*/