package com.myntra.apiTests.erpservices.lms.Helper;

import com.fasterxml.jackson.core.JsonParseException;
import com.myntra.apiTests.SERVICE_TYPE;
import com.myntra.apiTests.common.Constants.EnumSCM;
import com.myntra.apiTests.common.Constants.Headers;
import com.myntra.apiTests.common.ExceptionHandler;
import com.myntra.apiTests.erpservices.Constants;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.erpservices.rms.RMSServiceHelper;
import com.myntra.commons.codes.StatusResponse;
import com.myntra.commons.exception.ManagerException;
import com.myntra.commons.utils.Context;
import com.myntra.lms.client.domain.response.DomainShipmentUpdateResponse;
import com.myntra.lms.client.domain.response.PickupResponse;
import com.myntra.lms.client.response.OrderResponse;
import com.myntra.lms.client.response.OrderTrackingResponse;
import com.myntra.lms.client.response.TripOrderAssignmentResponse;
import com.myntra.lms.client.status.CourierCreationStatus;
import com.myntra.lms.client.status.ItemQCStatus;
import com.myntra.lms.client.status.ShipmentType;
import com.myntra.logistics.platform.domain.*;
import com.myntra.logistics.platform.domain.ml.MLShipmentUpdateEvent;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.oms.client.entry.OrderReleaseEntry;
import com.myntra.returns.common.enums.code.ReturnStatus;
import com.myntra.returns.response.ReturnResponse;
import com.myntra.test.commons.service.HTTPMethods;
import com.myntra.test.commons.service.HttpExecutorService;
import com.myntra.test.commons.service.Svc;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jettison.json.JSONException;
import org.joda.time.DateTime;
import org.testng.Assert;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.io.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.myntra.logistics.platform.domain.Premise.PremiseType.DELIVERY_CENTER;

/*import com.myntra.apiTests.erpservices.lms.Constants.EnumSCM;
import com.myntra.apiTests.erpservices.lms.Constants.Headers;*/

public class LMS_ReturnHelper {
	static Initialize init = new Initialize("/Data/configuration");
	String env = System.getenv("environment");
	static Logger log = Logger.getLogger(LMS_ReturnHelper.class);
	LMSHelper lmsHelper = new LMSHelper();
	LmsServiceHelper lmsServiceHelper= new LmsServiceHelper();
RMSServiceHelper rmsServiceHelper = new RMSServiceHelper();
	
	/**
	 * validateRmsLmsReturnCreation
	 *
	 * @param returnId
	 * @return
	 */
	
	public boolean validateOpenBoxRmsLmsReturnCreationV2(String returnId) throws IOException, ManagerException,
			JAXBException, InterruptedException, XMLStreamException, JSONException, SQLException {
		
		RMSServiceHelper rmsServiceHelper = new RMSServiceHelper();
		//Get the return_shipment details as a OrderResponse object, get return from RMS
		OrderResponse returnShipmentLMSObject = getReturnShipmentDetailsLMS(returnId);
		
		if(returnShipmentLMSObject.getOrders() != null && !returnShipmentLMSObject.getOrders().isEmpty()) {
			Map<String, Object> returnShipmentLMSDB = DBUtilities.exSelectQueryForSingleRecord("select * from return_shipment where source_return_id = " + returnId, "lms");
			
			Map<String, Object> returnRMSDB = DBUtilities.exSelectQueryForSingleRecord("select * from returns where id = " + returnId, "rms");
			ReturnResponse returnResponseRMSObj = rmsServiceHelper.getReturnDetailsNew(String.valueOf(Long.parseLong(returnId)));
			
			//Basic validations : return_type=OPEN_BOX, courier_code=ML
			Thread.sleep(Constants.LMS_PATH.sleepTime);
			ExceptionHandler.handleTrue(returnShipmentLMSObject.getOrders().get(0).getCourierOperator().equals("ML"), "Courier code assigned to this return is not ML");
			ExceptionHandler.handleTrue(returnShipmentLMSDB.get("return_type").equals("OPEN_BOX_PICKUP"), "The return type is not OPEN_BOX_PICKUP in LMS return");
			ExceptionHandler.handleTrue(returnRMSDB.get("return_mode").equals("OPEN_BOX_PICKUP"), "The return type is not OPEN_BOX_PICKUP in RMS return");
			
			//1. Validate if return_shipment  entry is created in LMS  with status "RETURN_CREATED"
			ExceptionHandler.handleTrue(validateReturnShipmentStatusInLMS(returnId, ShipmentStatus.RETURN_CREATED.toString(), 15), "Return-shipment is not created with status RETURN_CREATED in LMS");
			
			//2. Verify the status in RMS initially is LPI
			ExceptionHandler.handleTrue(rmsServiceHelper.validateReturnStatusInRMS(String.valueOf(Long.parseLong(returnId)), ReturnStatus.LPI.toString(), 15), "pickup is not created or not in LPI status in Returns");
			
			//3. Verify pickup is created with status PICKUP_CREATED
			ExceptionHandler.handleTrue(validatePickupShipmentStatusInLMS(returnId, ShipmentStatus.PICKUP_CREATED.toString(), 15), "Pickup-shipment is not created with status PICKUP_CREATED in LMS");
			
			//4. Verify the right return warehouse id  and return hub code is populated in return_shipment
			String orderId = returnResponseRMSObj.getData().get(0).getOrderId().toString();
			OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
			OrderReleaseEntry orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(omsServiceHelper.getReleaseId(orderId));
			String expectedReturnWHid = String.valueOf(orderReleaseEntry.getWarehouseId());
			
			String expectedReturnHub = lmsHelper.getReturnHubCodeForWarehouse.apply(Long.parseLong(expectedReturnWHid)).toString();
			String actualReturnHub = getReturnWarehouseHub(returnId);
			String actualReturnWHid = getReturnWarehouseId(returnId);
			ExceptionHandler.handleTrue(actualReturnWHid.equals(expectedReturnWHid), "The return warehouse id stamped in return_shipment is not = the return_hub_warehouse configured");
			ExceptionHandler.handleTrue(expectedReturnHub.equals(actualReturnHub), "The return hub stamped in return_shipment is not = the return_hub_warehouse configured");
			
			
			//5. Verify the DC code is populated correctly
			long actualDCcode = returnShipmentLMSObject.getOrders().get(0).getDeliveryCenterId();
			String packetId = omsServiceHelper.getPacketId(orderId);
			long expectedDCcode = (long) lmsHelper.getDCCodeForRelease.apply(packetId);
			ExceptionHandler.handleTrue(actualDCcode == expectedDCcode, "The DC code from order_release not equal to dc code stamped in return_shipment");
			
			//6. Verify return_shipment value = RMS refund value
//		ExceptionHandler.handleTrue(returnShipmentLMSObject.getOrders().get(0).getItemEntries().get(0).getItemValue().toString().equals(returnResponseRMSObj.getData().get(0).getReturnLineEntries().get(0).getReturnLineRefundDetailsEntry().getRefundAmount().toString()), "LMS Return_shipment shipment value not equal to RMS return refund value");
			
			
			//Verify return_shipment status = RIT
			ExceptionHandler.handleTrue(returnShipmentLMSObject.getOrders().get(0).getStatus().toString().equals(ShipmentStatus.valueOf("RETURN_CREATED").getShipmentStatusCode().toString()), "LMS return_shipment status is not RIT");
			
			//8. Verify rms (returns table) tracking number = LMS return_shipment tracking number
			log.info("The rms - return tracking number" + returnResponseRMSObj.getData().get(0).getReturnTrackingDetailsEntry());
			ExceptionHandler.handleTrue((returnResponseRMSObj.getData().get(0).getReturnTrackingDetailsEntry().getTrackingNo().toString().equals(returnShipmentLMSObject.getOrders().get(0).getTrackingNumber().toString())), "LMS return_shipment tracking number != RMS return tracking number");
			
			//9. Verify rms(return table) and lms (return_shipment) return entry data
			ExceptionHandler.handleTrue(returnShipmentLMSDB.get("return_type").toString().equals(returnResponseRMSObj.getData().get(0).getReturnMode().toString()), "return_mode/return_type Mismatch in LMS-RMS");
			
			//10 . Verify  LMS-RMS return mode
			ExceptionHandler.handleTrue(returnShipmentLMSDB.get("return_type").toString().equals(returnResponseRMSObj.getData().get(0).getReturnMode().toString()), "LMS RMS return_mode match error");
			
			//11. Verify RMS LMS email id
			ExceptionHandler.handleTrue(returnShipmentLMSObject.getOrders().get(0).getEmail().toString().equals(returnResponseRMSObj.getData().get(0).getEmail().toString()), "email Mismatch in LMS-RMS");
			
			String pickup_shipment_id = getAssociatedPickupForReturn(returnId);
			
			//12. Using the pickup id , get the tracking number stamped in Order tracking table,Verify pickup tracking number and order_tracking's orderId and TrackingNumber match
			String trackingNumber_orderTracking = getOrderTrackingNumber(pickup_shipment_id);
			com.myntra.lms.client.domain.response.PickupResponse pickupResponse = getPickupShipmentDetailsLMS(pickup_shipment_id);
			String trackingNumber_pickupShipment = pickupResponse.getDomainPickupShipments().get(0).getTrackingNumber();
			
			ExceptionHandler.handleTrue(trackingNumber_orderTracking.toString().equals(trackingNumber_pickupShipment.toString()), "Order Tracking tracking_number !+ pickup_shipment tracking number");
			
			Map<String, Object> orderTrackingLMSDB = DBUtilities.exSelectQueryForSingleRecord("select * from order_tracking where tracking_no = '" + trackingNumber_pickupShipment + "'", "lms");
			
			//13. Verify Order_tracking statuses
			ExceptionHandler.handleTrue(returnShipmentLMSDB.get("courier_code").toString().equals("ML"), "courier Mismatch in LMS-RMS");
			validateOrderTrackingShipmentStatus(trackingNumber_pickupShipment, ShipmentStatus.PICKUP_CREATED);
			validateOrderTrackingCourierCreationStatus(trackingNumber_pickupShipment, CourierCreationStatus.NOT_INITIATED);
			validateOrderTrackingDeliveryStatus(trackingNumber_pickupShipment, ShipmentStatus.RETURN_CREATED.getOrderTrackingStatusCode().toString());
			ExceptionHandler.handleTrue(orderTrackingLMSDB.get("shipment_status").toString().equals(ShipmentStatus.PICKUP_CREATED.toString()), "order_tracking table shipment_status is not PICKUP_CREATED");
			
			//14. Verify order_Tracking_details
			List<String> activityTypeStatusCombination = new ArrayList<>();
			activityTypeStatusCombination.add("PICKUP_CREATED:PICKUP_CREATED:PICKUP_CREATED");
			activityTypeStatusCombination.add("PICKUP_DETAILS_UPDATED:PICKUP_CREATED:PICKUP_CREATED");
			//TODO: Check why failing
			//validateOrderTrackingDetailsForActivity(trackingNumber_orderTracking,activityTypeStatusCombination);
		} else {
			
			 log.error("ReturnShipmentLMSObject is null or empty - "+returnShipmentLMSObject);
		}
		return true;
	}
	
	private void validateOrderTrackingCourierCreationStatus(String trackingNumber, CourierCreationStatus status) throws ManagerException {
		Map<String, Object> orderTrackingLMSDB = DBUtilities.exSelectQueryForSingleRecord("select * from order_tracking where tracking_no = '" + trackingNumber + "'", "lms");
		ExceptionHandler.handleTrue(orderTrackingLMSDB.get("courier_creation_status").equals(status.toString()), "order_Tracking table - courier_creation_status is not " + status);
	}
	
	private void validateOrderTrackingShipmentStatus(String trackingNumber, ShipmentStatus status) throws ManagerException {
		Map<String, Object> orderTrackingLMSDB = DBUtilities.exSelectQueryForSingleRecord("select * from order_tracking where tracking_no = '" + trackingNumber + "'", "lms");
		ExceptionHandler.handleTrue(orderTrackingLMSDB.get("shipment_status").equals(status.toString()), "order_Tracking table - shipment status is not " + status);
		
	}

	
	public void validateMLShipmentStatus(String trackingNumber, String status) throws ManagerException {
		Map<String, Object> orderTrackingLMSDB = DBUtilities.exSelectQueryForSingleRecord("select * from ml_shipment where tracking_number = '" + trackingNumber + "'", "lms");
		ExceptionHandler.handleTrue(orderTrackingLMSDB.get("shipment_status").equals(status.toString()), "ML_shipment table - shipment status is not " + status);
	}
	
	
	private void validateOrderTrackingDeliveryStatus(String trackingNumber, String status) throws ManagerException {
		Map<String, Object> orderTrackingLMSDB = DBUtilities.exSelectQueryForSingleRecord("select * from order_tracking where tracking_no = '" + trackingNumber + "'", "lms");
		ExceptionHandler.handleTrue(orderTrackingLMSDB.get("delivery_status").equals(status.toString()), "order_Tracking table - delivery status is not " + status);
	}
	
	private String getReturnWarehouseId(String returnId) {
		try {
			List list = DBUtilities.exSelectQuery("select return_warehouse_id from return_shipment where source_return_id =" + returnId, "lms");
			if (list == null) {
				return "false";
			}
			HashMap<String, Object> hm = (HashMap<String, Object>) list.get(0);
			return "" + hm.get("return_warehouse_id");
		} catch (Exception e) {
			log.error("Error in return_warehouse_id :- " + e.getMessage());
			return "false";
		}
	}
	
	private String getReturnWarehouseHub(String returnId) {
		try {
			List list = DBUtilities.exSelectQuery("select return_hub_code from return_shipment where source_return_id =" + returnId, "lms");
			if (list == null) {
				return "false";
			}
			HashMap<String, Object> hm = (HashMap<String, Object>) list.get(0);
			return "" + hm.get("return_hub_code");
		} catch (Exception e) {
			log.error("Error in return_hub_code :- " + e.getMessage());
			return "false";
		}
	}
	
	//GLORIA : added for ReturnsReengineering
	public void validateOrderTrackingDetailsForActivity(String tracking_no, List<String> ActivityTypeStatusCombination) {
		
		ActivityTypeStatusCombination.forEach(status -> {
			String[] temp = status.split(":");
			String activityType = temp[0];
			String fromStatus = temp[1];
			String toStatus = temp[2];
			try {
				ExceptionHandler.handleTrue(DBUtilities.exSelectQueryForSingleRecord("select * from order_tracking_detail where activity_type = '" + activityType + "' and " +
						"from_status = '" + fromStatus + "' and to_status = '" + toStatus + "' and order_tracking_id = (select id from order_tracking where tracking_no='" + tracking_no + "')", "lms") != null, "");
			} catch (ManagerException e) {
				e.printStackTrace();
			}
		});
	}
	//GLORIA : added for ReturnsReengineering
	
	
	/**
	 * validateReturnShipmentStatusInLMS - This goes to the retrun_shipment table and checks for status
	 *
	 * @param returnId
	 * @param status
	 * @param delaytoCheck
	 * @return
	 */
	
	public boolean validateLMS_ReturnStatus(String returnId, String status, int delaytoCheck) {
		log.info("Validate return  Shipment Status in LMS return_shipment table");
		boolean validateStatus = false;
		try {
			for (int i = 0; i < delaytoCheck; i++) {
				String status_code = getShipmentStatusFromLMS_return_shipment(returnId);
				if (status_code.equalsIgnoreCase(status) || status_code.equalsIgnoreCase(status)) {
					validateStatus = true;
					break;
				} else {
					Thread.sleep(4000);
					validateStatus = false;
				}
				
				log.info("waiting for return Status in LMS" + status + " .current status=" + status_code + "\t " + i);
			}
		} catch (Exception e) {
			e.printStackTrace();
			validateStatus = false;
		}
		return validateStatus;
	}
	//GLORIA : added for ReturnsReengineering
	
	
	/**
	 * This function gives the ShipmentStatus from LMS : return_shipment
	 *
	 * @param returnId
	 * @return
	 */
	public String getShipmentStatusFromLMS_return_shipment(String returnId) {
		try {
			List list = DBUtilities.exSelectQuery("select shipment_status from return_shipment where source_return_id =" + returnId, "lms");
			if (list == null) {
				return "false";
			}
			HashMap<String, Object> hm = (HashMap<String, Object>) list.get(0);
			return "" + hm.get("shipment_status");
		} catch (Exception e) {
			log.error("Error in getOrderStatusFromLMS :- " + e.getMessage());
			return "false";
		}
	}
	
	//GLORIA : added for ReturnsReengineering
	public String getAssociatedPickupForReturn(String sourceReturnId) {
		try {
			Thread.sleep(Constants.LMS_PATH.sleepTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String pickup_shipment_idQuery = "select pickup_shipment_id_fk from return_pickup_association where return_shipment_id_fk in (select id from return_shipment where source_return_id = " + sourceReturnId + ")";
		List<Map<String, Object>> TxQ = DBUtilities.exSelectQuery(pickup_shipment_idQuery, "lms");
		Map<String, Object> txResult = TxQ.get(0);
		String pickup_shipment_id = txResult.get("pickup_shipment_id_fk").toString();
		return pickup_shipment_id;
	}
	
	//GLORIA : added for ReturnsReengineering
	public String getOrderTrackingNumber(String pickupShipmentId) {
		String pickupTrackingNumberQuery = " select tracking_no from order_tracking where pickup_id =" + Integer.valueOf(pickupShipmentId);
		List<Map<String, Object>> TxQ = DBUtilities.exSelectQuery(pickupTrackingNumberQuery, "lms");
		Map<String, Object> txResult = TxQ.get(0);
		String pickupTrackingNumber = txResult.get("tracking_no").toString();
		return pickupTrackingNumber;
		
	}
	//GLORIA : added for ReturnsReengineering
	
	
	/**
	 * This method gives the pickup details  details by passing the pickupId
	 *
	 * @param pickupShipmentID
	 * @return
	 * @throws IOException
	 * @throws JAXBException
	 */
	
	public com.myntra.lms.client.domain.response.PickupResponse getPickupShipmentDetailsLMS(String pickupShipmentID) throws IOException {
		
		String pathParam = "findById?pickupId=" + Integer.valueOf(pickupShipmentID);
		Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.
						PICKUP_BY_ID, new String[]{pathParam}, SERVICE_TYPE.LMS_SVC.toString(),
				HTTPMethods.GET, null, Headers.getLmsHeaderJSON());
		
		com.myntra.lms.client.domain.response.PickupResponse response = (com.myntra.lms.client.domain.response.PickupResponse) APIUtilities.getJsontoObjectUsingFasterXML(service.getResponseBody(),
				new com.myntra.lms.client.domain.response.PickupResponse());
		return response;
	}
	
	//GLORIA : added for ReturnsReengineering
	public OrderTrackingResponse getOrderTrackingDetailV2Json(String courierCode, String trackingNumber) throws IOException, JAXBException {
		Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.GET_ORDER_TRACKING_DETAIL_V2, new String[]{"getOrderTrackingDetail?courierOperator=" + courierCode + "&trackingNumber=" + trackingNumber + "&level=LEVEL2"}, SERVICE_TYPE.LMS_SVC.toString(),
				HTTPMethods.GET, null, Headers.getLmsHeaderJSON());
		OrderTrackingResponse response = (OrderTrackingResponse) APIUtilities.getJsontoObject(service.getResponseBody(),
				new OrderTrackingResponse());
		return response;
	}
	//GLORIA : change of processReturnInLMS because of ReturnsReengineering
	
	/**
	 * processOpenBoxReturn : processes  the OpenBox return return till Shipping back to config source warehouse and
	 * receiving in WH. SO it performs the complete action
	 *
	 * @param returnId/in case of selfShip its orderId
	 * @param toStatus
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws NumberFormatException
	 * @throws IOException
	 * @throws JAXBException
	 * @throws InterruptedException
	 */
	public void processOpenBoxReturn(String returnId, String toStatus) throws InterruptedException, JAXBException, IOException, ManagerException, XMLStreamException, JSONException, SQLException {
		String destWarehouseId = null;
		String deliveryCenterID = null;
		String tripOrderAssignmentId = null;
		RMSServiceHelper rmsServiceHelper = new RMSServiceHelper();
		
		ExceptionHandler.handleTrue(rmsServiceHelper.getReturnDetailsNew(String.valueOf(Long.parseLong(returnId))).getData().get(0).getReturnMode().toString().equals(EnumSCM.OPEN_BOX_PICKUP), "");
		
		//1. Validate the respective RMS and LMS return fields
		validateOpenBoxRmsLmsReturnCreationV2("" + returnId);
		
		manifestOpenBoxPickups(returnId);
		
		//TODO : rmsObject does not have delivery centre id , so comparing it with lms objectResponse- after manifestation only the dccode, wh and tracking number are updated in RMS
		//	ExceptionHandler.handleTrue(returnShipmentLMSDB.get("delivery_center_id").toString().equals((returnShipmentLMSObject.getOrders().get(0).getDeliveryCenterId().toString())), "return_warehouse_id mismatch");
		//TODO : getting below as null:returnShipmentLMSObject.getOrders().get(0).getReturnWarehouseId()- check after a delay
		//	ExceptionHandler.handleTrue(returnShipmentLMSObject.getOrders().get(0).getReturnWarehouseId().toString().equals(returnResponseRMSObj.getData().get(0).getReturnAdditionalDetailsEntry().getIdealReturnWarehouse().toString()), "return_warehouse_id mismatch");
		//TODO : getting returnResponseRMSObj.getData().get(0).getReturnAdditionalDetailsEntry().getIdealReturnWarehouse() as null-- check after a delay
		//	ExceptionHandler.handleTrue(returnShipmentLMSDB.get("return_warehouse_id").toString().equals(returnResponseRMSObj.getData().get(0).getReturnAdditionalDetailsEntry().getIdealReturnWarehouse().toString()), "return_warehouse_id mismatch");
		//TODO getting returnShipmentLMSObject.getOrders().get(0).getCourierOperator() null
		//	ExceptionHandler.handleTrue(returnShipmentLMSObject.getOrders().get(0).getCourierOperator().toString().equals(returnResponseRMSObj.getData().get(0).getEmail().toString()), "email Mismatch in LMS-RMS");
		//	ReturnHubWarehouseResponse repsonse = (ReturnHubWarehouseResponse)lmsServiceHelper.getReturnHubFromWarehouse.apply(returnShipmentLMSDB.get("return_warehouse_id").toString());
		//			ExceptionHandler.handleTrue(returnShipmentLMSDB.get("return_hub_code").toString().equals((repsonse.getReturnProcessingConfigEntries().get(0).getHubCode().toString())), "return_warehouse_id mismatch");
		Thread.sleep(5000);
		String trackingNo=null;
		OrderResponse returnShipmentLMSObject = getReturnShipmentDetailsLMS(returnId);
		
		if(returnShipmentLMSObject.getOrders()!=null && !returnShipmentLMSObject.getOrders().isEmpty() ) {
			 trackingNo = returnShipmentLMSObject.getOrders().get(0).getTrackingNumber();
		}
		else
			log.info("Failed here : returnShipmentLMSObject.getOrders().get(0).getTrackingNumber(): "+returnShipmentLMSObject.getOrders().get(0).getTrackingNumber());
		Map<String, Object> return_shipment = DBUtilities.exSelectQueryForSingleRecord("select * from return_shipment where source_return_id = " + returnId, "lms");
		if (return_shipment.get("courier_code").toString().equals("ML"))
			processReturnInML(returnId, toStatus, trackingNo, destWarehouseId, deliveryCenterID, tripOrderAssignmentId, return_shipment);
		else if (return_shipment.get("courier_code").toString().equals("EK")) {
		
		} else if (return_shipment.get("courier_code").toString().equals("DE")) {
		
		}
		
		log.info("Your order processing has been completed successfully ReturnId: " + returnId);
	}
	
	/**
	 * processReturnInML
	 * @param returnId
	 * @param toStatus
	 * @param trackingNo
	 * @param destWarehouseId
	 * @param deliveryCenterID
	 * @param tripOrderAssignmentId
	 * @throws IOException
	 * @throws JAXBException
	 * @throws InterruptedException
	 */
	public void processReturnInML(String returnId, String toStatus, String trackingNo, String destWarehouseId, String
			deliveryCenterID, String tripOrderAssignmentId, Map<String, Object> return_shipment) throws IOException, JAXBException, InterruptedException, ManagerException, XMLStreamException, JSONException {
		Map<String, String> data;
		if (!toStatus.equals(EnumSCM.SELF_SHIP)) {
			trackingNo = return_shipment.get("tracking_number").toString();
			destWarehouseId = return_shipment.get("return_warehouse_id").toString();
			deliveryCenterID = return_shipment.get("delivery_center_id").toString();
			data = lmsServiceHelper.processReturnTillTripCreation(returnId, trackingNo, deliveryCenterID);
			tripOrderAssignmentId = data.get("tripOrderAssignmentId");
		}
		switch (toStatus) {
			case EnumSCM.PICKED_UP_SUCCESSFULLY:
				markTripPickupSuccessful(returnId, destWarehouseId, deliveryCenterID, tripOrderAssignmentId);
				break;
			
			case EnumSCM.PICKED_UP_QCP_APPROVED_After_trip_close:
				pickupPQCP_CCApproveReject(returnId, destWarehouseId, deliveryCenterID, tripOrderAssignmentId, trackingNo,"APPROVED");
				break;
			case EnumSCM.PICKED_UP_QCP_REJECTED_After_trip_close:
				pickupPQCP_CCApproveReject(returnId, destWarehouseId, deliveryCenterID, tripOrderAssignmentId, trackingNo,"REJECTED");
				break;
			case EnumSCM.PICKUP_DONE_QC_PENDING:
				PQCP_Approve(returnId, destWarehouseId, deliveryCenterID, tripOrderAssignmentId, trackingNo,"APPROVED");
				break;
			
			default:
				log.info("No matching status");
				break;
		}
	}
	
	/**
	 * markTripPickupSuccessful
	 * @param returnId
	 * @param destWarehouseId
	 * @param deliveryCenterID
	 * @param tripOrderAssignmentId
	 * @throws Exception
	 */
	private void markTripPickupSuccessful(String returnId, String destWarehouseId,
	                                      String deliveryCenterID, String tripOrderAssignmentId)
			throws InterruptedException, IOException, JAXBException, JSONException, XMLStreamException, ManagerException {
		TripOrderAssignmentResponse response = lmsServiceHelper.updatePickupInTrip(Long.parseLong(tripOrderAssignmentId), EnumSCM.PICKED_UP_SUCCESSFULLY, EnumSCM.UPDATE);
		Assert.assertEquals(response.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to update orders in Trip.");
		Thread.sleep(2000);
		TripOrderAssignmentResponse response1 = lmsServiceHelper.updatePickupInTrip(Long.parseLong(tripOrderAssignmentId), EnumSCM.PICKED_UP_SUCCESSFULLY, EnumSCM.TRIP_COMPLETE);
		Assert.assertEquals(response1.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to complete Trip.");
		// Assert.assertTrue(lms_returnHelper.validatePickupShipmentStatusInLMS(returnId,EnumSCM.PICKUP_SUCCESSFUL,5), "return is not PICKUP_SUCCESSFUL" );
		validateRmsStatusAndRefund(returnId,ReturnStatus.RL.toString(), true, Constants.LMS_PATH.sleepTime);
		rmsServiceHelper.WaitRefundStatus(returnId,20);
		TMSServiceHelper tmsServiceHelper= new TMSServiceHelper();
		long masterBagId = (long)tmsServiceHelper.createNcloseMBforReturn.apply(returnId);
		tmsServiceHelper.processInTMSFromClosedToReturnHub.accept(masterBagId);
		
		
		Assert.assertEquals(lmsServiceHelper.masterBagInScan(masterBagId, EnumSCM.RECEIVED, "Bangalore", 36, "WH")
				.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to inscan masterBag at WH");
		Assert.assertEquals(
				lmsServiceHelper.receiveReturnShipmentFromMasterbag(masterBagId, String.valueOf(returnId)).getStatus().getStatusType().toString(),
				EnumSCM.SUCCESS, "Unable to receive shipment in DC");
		
		
		ExceptionHandler.handleTrue(validateReturnShipmentStatusInLMS(returnId, ShipmentStatus.DELIVERED_TO_SELLER.toString(),10));
		lmsServiceHelper.validateRmsStatusAndRefund(returnId,EnumSCM.RRC, true, Constants.LMS_PATH.sleepTime);
		
	}
	public void manifestOpenBoxPickups(String returnId) throws IOException, JAXBException, InterruptedException,
			ManagerException {
		Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.OPEN_BOX_MANIFEST, null, SERVICE_TYPE.LMS_SVC.toString(), HTTPMethods.POST, null, Headers.getLmsHeaderJSON());
		while (!isPickupManifested(returnId)) {
			Thread.sleep(Constants.LMS_PATH.sleepTime);
			
			service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.OPEN_BOX_MANIFEST, null, SERVICE_TYPE.LMS_SVC.toString(), HTTPMethods.POST, null, Headers.getLmsHeaderJSON());
			
		}
		com.myntra.lms.client.domain.response.PickupResponse pickupResponse = (com.myntra.lms.client.domain.response.PickupResponse) APIUtilities.getJsontoObjectUsingFasterXML(service.getResponseBody(),
				new com.myntra.lms.client.domain.response.PickupResponse());
		
		pickupResponse.getStatus().getStatusType().equals("SUCCESS");
		//Verify order_tracking courier creation status = APPROVED
		validateOrderTrackingCourierCreationStatus(pickupResponse.getDomainPickupShipments().get(0).getTrackingNumber().toString(), CourierCreationStatus.ACCEPTED);
		//validateMLShipmentStatus(pickupResponse.getDomainPickupShipments().get(0).getTrackingNumber().toString(),com.myntra.ml.domain.MLDeliveryShipmentStatus)
	}
	
	public boolean isPickupManifested(String returnId) throws IOException {
		String pickup_shipment_id = getAssociatedPickupForReturn(returnId);
		
		com.myntra.lms.client.domain.response.PickupResponse pickupResponse = getPickupShipmentDetailsLMS(pickup_shipment_id);
		return pickupResponse.getDomainPickupShipments().get(0).getManifested();
	}
	//Gloria : adding
	
	/**
	 * This method gives the return_shipment details in the form of ObjectResponse object
	 *
	 * @param returnId
	 * @return
	 * @throws IOException
	 * @throws JAXBException
	 */
	
	public OrderResponse getReturnShipmentDetailsLMS(String returnId)
			throws IOException, JAXBException {
		Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.RETURN, new String[]{returnId}, SERVICE_TYPE.LMS_SVC.toString(),
				HTTPMethods.GET, null, Headers.getLmsHeaderXML());
		OrderResponse response = (OrderResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(),
				new OrderResponse());
		return response;
	}
	
	/**
	 * validateReturnStatusInLMS
	 *
	 * @param returnId
	 * @param status
	 * @param delaytoCheck
	 * @return
	 */
	public boolean validateReturnShipmentStatusInLMS(String returnId, String status, int delaytoCheck) {
		log.info("Validate return Status in LMS Return Shipment table");
		boolean validateStatus = false;
		try {
			for (int i = 0; i < delaytoCheck; i++) {
				String status_code = getReturnShipmentStatusFromLMS(returnId);
				if (status_code.equalsIgnoreCase(status) || status_code.equalsIgnoreCase(status)) {
					validateStatus = true;
					break;
				} else {
					Thread.sleep(4000);
					validateStatus = false;
				}
				
				log.info("waiting for return Status in LMS" + status + " .current status=" + status_code + "\t " + i);
			}
		} catch (Exception e) {
			e.printStackTrace();
			validateStatus = false;
		}
		return validateStatus;
	}
	
	/**
	 * getReturnStatusFromLMS
	 *
	 * @param returnId
	 * @return
	 */
	public String getReturnShipmentStatusFromLMS(String returnId) {
		try {
			List list = DBUtilities.exSelectQuery("select shipment_status from return_shipment where source_return_id =" + returnId, "lms");
			if (list == null) {
				return "false";
			}
			HashMap<String, Object> hm = (HashMap<String, Object>) list.get(0);
			return "" + hm.get("shipment_status");
		} catch (Exception e) {
			log.error("Error in getOrderStatusFromLMS :- " + e.getMessage());
			return "false";
		}
	}
	
	/**
	 * validatePickupStatusInLMS
	 *
	 * @param returnId
	 * @param status
	 * @param delaytoCheck
	 * @return
	 */
	public boolean validatePickupShipmentStatusInLMS(String returnId, String status, int delaytoCheck) {
		log.info("Validate pickup Status in LMS Pickup Shipment table");
		boolean validateStatus = false;
		try {
			for (int i = 0; i < delaytoCheck; i++) {
				String status_code = getPickupShipmentStatusFromLMS(returnId);
				if (status_code.equalsIgnoreCase(status) || status_code.equalsIgnoreCase(status)) {
					validateStatus = true;
					break;
				} else {
					Thread.sleep(Constants.LMS_PATH.sleepTime);
					validateStatus = false;
				}
				
				log.info("waiting for pickup Status in LMS" + status + " .current status=" + status_code + "\t " + i);
			}
		} catch (Exception e) {
			e.printStackTrace();
			validateStatus = false;
		}
		return validateStatus;
	}
	
	/**
	 * getReturnStatusFromLMS
	 *
	 * @param returnId
	 * @return
	 */
	public String getPickupShipmentStatusFromLMS(String returnId) {
		try {
			List list = DBUtilities.exSelectQuery("select shipment_status from pickup_shipment where id in (select " +
					"pickup_shipment_id_fk from return_pickup_association where return_shipment_id_fk in  (select id " +
					"from return_shipment where   source_return_id in (" + returnId + ")))", "lms");
			if (list == null) {
				return "false";
			}
			HashMap<String, Object> hm = (HashMap<String, Object>) list.get(0);
			return "" + hm.get("shipment_status");
		} catch (Exception e) {
			log.error("Error in getOrderStatusFromLMS :- " + e.getMessage());
			return "false";
		}
	}
	
	/**
	 * validateRmsStatusAndRefund
	 *
	 * @param returnId
	 * @param status
	 * @param refund
	 */
	public boolean validateRmsStatusAndRefund(String returnId, String status, boolean refund, long wait) throws IOException, InterruptedException {
		RMSServiceHelper rmsServiceHelper = new RMSServiceHelper();
		Assert.assertTrue(rmsServiceHelper.validateReturnStatusInRMS(returnId, status, 10), "pickup is not in " + status + " status in Returns");
		if (refund) {
			log.info("sleeping for " + wait + " msc");
			Thread.sleep(wait);
			ReturnResponse returnResponse = rmsServiceHelper.getReturnDetailsNew(returnId);
			Assert.assertEquals(returnResponse.getData().get(0).getReturnRefundDetailsEntry().getRefunded().toString(), "" + refund, "is_refunded is not as expected. Expected: " + refund + " But not found");
			Assert.assertNotNull(returnResponse.getData().get(0).getReturnRefundDetailsEntry().getRefundPPSId().toString(), "Return PPSID is null in RMS");
		}
		return true;
	}
	
	public void uploadClosedBoxStatusFile(String filePath) throws UnsupportedEncodingException, JAXBException, InterruptedException {
		MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
		File file = new File(filePath);
		multipartEntityBuilder.addPart("file", new FileBody(file));
		multipartEntityBuilder.setContentType(ContentType.APPLICATION_OCTET_STREAM);
		
		Svc service = HttpExecutorService.executeHttpServiceForUpload(Constants.LMS_PATH.UPLOAD_CLOSEDBOX_STATUS, null
				, SERVICE_TYPE.LMS_SVC.toString(), HTTPMethods.POST, multipartEntityBuilder, getTokenForFileUpload());
		
		//TODO: Check why marshalling is failing
	/*	PickupResponse response = (PickupResponse) APIUtilities
				.convertXMLStringToObject(service.getResponseBody(), new PickupResponse());
		
		Assert.assertEquals(response.getStatus().getStatusMessage(),
				"Request is submitted. Please check status of the request and download it once complete from the Download Document section .");
*/
	}
	
	public HashMap<String, String> getTokenForFileUpload() {
		HashMap<String, String> stnHeaders = new HashMap<String, String>();
		stnHeaders.put("Authorization", "Basic RVJQIEFkbWluaXN0cmF0b3J+ZXJwYWRtaW46dGVzdA==");
		stnHeaders.put("Content-Type", "multipart/form-data");
		return stnHeaders;
	}
	
	/**
	 * processClosedBoxPickup
	 *
	 * @param returnId
	 * @param toStatus
	 * @throws IOException
	 * @throws JAXBException
	 * @throws InterruptedException
	 */
	public void processClosedBoxPickup(String returnId, String toStatus) throws IOException, JAXBException, InterruptedException, ManagerException {
		switch (toStatus) {
			case EnumSCM.PICKED_UP_SUCCESSFULLY:
				pickupSuccessFullyClosedBox(returnId,ItemQCStatus.PASSED);
				break;
			case EnumSCM.PICKUP_SUCCESSFUL_ONHOLD_APPROVE:
				PickupSuccessfulApproveRejectClosedBox(returnId,ItemQCStatus.ON_HOLD,"APPROVED");
				break;
			case EnumSCM.PICKUP_SUCCESSFUL_ONHOLD_REJECTED:
				PickupSuccessfulApproveRejectClosedBox(returnId,ItemQCStatus.ON_HOLD,"REJECTED");
				break;
		/*	case EnumSCM.PICKUP_SUCCESSFUL_CB_AT_RPU:
				pickupSuccessFulClosedBoxAtRPU(returnId);
				break;
			case EnumSCM.ONHOLD_PICKUP_WITH_DC_APPROVE:
				pickupSuccessFulClosedBoxOnHoldApprove(returnId);
				break;
			case EnumSCM.ONHOLD_PICKUP_WITH_DC_REJECT:
				pickupSuccessFulClosedBoxOnHoldReject(returnId);
				break;
			default:
				log.info("No matching status");
				break;*/
		}
	}
	public void PickupSuccessfulApproveRejectClosedBox(String returnId,ItemQCStatus status,String approveReject) throws IOException, JAXBException, InterruptedException,
			ManagerException {
		
		String tracking_number = getPickupTrackingNoOfReturn(returnId);
		
		String courier_code = "DE";
		String shipment_type = "PU";
		String activity_type1 = "PICKUP_DETAILS_UPDATED";
		String activity_sub_type1 = "SHIPMENT_ACCEPTED_BY_COURIER";
		validateOrderTrackingCourierCreationStatus(tracking_number, CourierCreationStatus.NOT_INITIATED);
		
		String file_path = generateClosedBoxStatusCSV(tracking_number, courier_code, shipment_type, activity_type1, activity_sub_type1);
		uploadClosedBoxStatusFile(file_path);
		
		
		String activity_type2 = "OUT_FOR_PICKUP";
		String activity_sub_type2 = "";
		String file_path2 = generateClosedBoxStatusCSV(tracking_number, courier_code, shipment_type, activity_type2, activity_sub_type2);
		uploadClosedBoxStatusFile(file_path2);
		Assert.assertTrue(validatePickupShipmentStatusInLMS(returnId, ShipmentStatus.OUT_FOR_PICKUP.toString(), 15));
		
		String activity_type3 = "PICKUP_DONE";
		String activity_sub_type3 = "";
		String file_path3 = generateClosedBoxStatusCSV(tracking_number, courier_code, shipment_type, activity_type3, activity_sub_type3);
		uploadClosedBoxStatusFile(file_path3);
		Assert.assertTrue(validatePickupShipmentStatusInLMS(returnId, ShipmentStatus.PICKUP_DONE.toString(), 15));
		Assert.assertTrue(validateReturnShipmentStatusInLMS(returnId, ShipmentStatus.PICKUP_DONE.toString(), 15));
		
		updateReturnReceiveEvents(returnId,"DE");
		String pickupId = getPickupIdOfReturn(returnId);
		
		Assert.assertTrue(validatePickupShipmentStatusInLMS(returnId, ShipmentStatus.RECEIVED_AT_RETURNS_PROCESSING_CENTER.toString(),15));
		Assert.assertTrue(validateReturnShipmentStatusInLMS(returnId, ShipmentStatus.RECEIVED_AT_RETURNS_PROCESSING_CENTER.toString(),15));
		
		
		performClosedBoxReturnQC(returnId,status,courier_code);
		//Just to process the remaining returns in the pickup randomly , we mark the even return ids on_hold and odd ones shortage
		returnApproveOrRejectClosedBox(returnId,approveReject);
	}
	
	public void pickupSuccessFullyClosedBox(String returnId,ItemQCStatus status) throws IOException, JAXBException, InterruptedException,
			ManagerException {
		
		String tracking_number = getPickupTrackingNoOfReturn(returnId);
		
		String courier_code = "DE";
		String shipment_type = "PU";
		String activity_type1 = "PICKUP_DETAILS_UPDATED";
		String activity_sub_type1 = "SHIPMENT_ACCEPTED_BY_COURIER";
		validateOrderTrackingCourierCreationStatus(tracking_number, CourierCreationStatus.NOT_INITIATED);
		
		String file_path = generateClosedBoxStatusCSV(tracking_number, courier_code, shipment_type, activity_type1, activity_sub_type1);
		uploadClosedBoxStatusFile(file_path);
		
		
		String activity_type2 = "OUT_FOR_PICKUP";
		String activity_sub_type2 = "";
		String file_path2 = generateClosedBoxStatusCSV(tracking_number, courier_code, shipment_type, activity_type2, activity_sub_type2);
		uploadClosedBoxStatusFile(file_path2);
		Assert.assertTrue(validatePickupShipmentStatusInLMS(returnId, ShipmentStatus.OUT_FOR_PICKUP.toString(), 15));
		
		String activity_type3 = "PICKUP_DONE";
		String activity_sub_type3 = "";
		String file_path3 = generateClosedBoxStatusCSV(tracking_number, courier_code, shipment_type, activity_type3, activity_sub_type3);
		uploadClosedBoxStatusFile(file_path3);
		Assert.assertTrue(validatePickupShipmentStatusInLMS(returnId, ShipmentStatus.PICKUP_DONE.toString(), 15));
		Assert.assertTrue(validateReturnShipmentStatusInLMS(returnId, ShipmentStatus.PICKUP_DONE.toString(), 15));
		
		updateReturnReceiveEvents(returnId,"DE");
		String pickupId = getPickupIdOfReturn(returnId);
		
		Assert.assertTrue(validatePickupShipmentStatusInLMS(returnId, ShipmentStatus.RECEIVED_AT_RETURNS_PROCESSING_CENTER.toString(),15));
		Assert.assertTrue(validateReturnShipmentStatusInLMS(returnId, ShipmentStatus.RECEIVED_AT_RETURNS_PROCESSING_CENTER.toString(),15));
		
		
		performClosedBoxReturnQC(returnId,status,courier_code);
		//Just to process the remaining returns in the pickup randomly , we mark the even return ids on_hold and odd ones shortage
	}
	
	public void performClosedBoxReturnQC(String returnId, ItemQCStatus status, String courier_code) throws IOException {
		//Find the pickup id
		String pickupId = getPickupIdOfReturn(returnId);
		String tracking_number = getPickupTrackingNoOfReturn(returnId);
		
		List<String> returnList = getReturnsInPickup(pickupId);
		//We have to process all the returns in the pickup, the one passed as a parameter will be processed for the status which is passed, rest of the returns will be randomly processed
		returnList.remove(returnId);
		
		Map<String, ItemQCStatus> returnStatusMap = new HashMap<>();
		for (String remainingReturn : returnList) {
			if (Integer.valueOf(remainingReturn) % 2 == 0) {
				returnStatusMap.put(remainingReturn, ItemQCStatus.ON_HOLD);
				log.info("Return- " + remainingReturn + " Status-> On hold");
				
			} else {
				returnStatusMap.put(remainingReturn, ItemQCStatus.SHORTAGE);
				log.info("Return- " + remainingReturn + " Status-> SHORTAGE");
				
			}
		}
		ArrayList<ReturnShipmentUpdate> returnShipmentUpdates = new ArrayList<>();
		
		ReturnShipmentUpdate returnShipmentUpdate1 = new ReturnShipmentUpdate();
		returnShipmentUpdate1.setSourceReturnId(returnId);
		returnShipmentUpdate1.setRemark("Remark");
		returnShipmentUpdate1.setItemQCStatus(status);
		returnShipmentUpdates.add(returnShipmentUpdate1);
		
		for (Map.Entry<String, ItemQCStatus> entry : returnStatusMap.entrySet()) {
			ReturnShipmentUpdate returnShipmentUpdate = new ReturnShipmentUpdate();
			returnShipmentUpdate.setSourceReturnId(entry.getKey());
			returnShipmentUpdate.setRemark("Remark");
			returnShipmentUpdate.setItemQCStatus(entry.getValue());
			returnShipmentUpdates.add(returnShipmentUpdate);
		}
		
		
		ClosedBoxPickupQCCompleteUpdate closedBoxPickupQCCompleteUpdate = new ClosedBoxPickupQCCompleteUpdate(pickupId,
				courier_code, tracking_number, ShipmentUpdateEvent.QUALITY_CHECK_COMPLETE, null, 5L,
				new Premise("5", Premise.PremiseType.DELIVERY_CENTER), "Location", new DateTime(), "remarks", ShipmentType.PU, ShipmentUpdateActivityTypeSource.LogisticsPortal, "Gloria", null, returnShipmentUpdates);
		String payload = APIUtilities.getObjectToJSON(closedBoxPickupQCCompleteUpdate);
		
		Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.UPDATE_CLOSEDBOX_QC_STATUS, null,
				SERVICE_TYPE.LMS_SVC.toString(), HTTPMethods.POST, payload, Headers.getLmsHeaderJSON());
		
		//TODO :
		/*	DomainShipmentUpdateResponse domainShipmentUpdateResponse = (DomainShipmentUpdateResponse) APIUtilities.getJsontoObject(service.getResponseBody(),
					new DomainShipmentUpdateResponse());
			
			Assert.assertTrue(domainShipmentUpdateResponse.getStatus().getStatusType().equals(StatusResponse.Type.SUCCESS));
		*/
		
		for (Map.Entry<String, ItemQCStatus> entry : returnStatusMap.entrySet()) {
			if(entry.getValue().toString().equals( ItemQCStatus.ON_HOLD.toString())) {
				
				Assert.assertTrue(validateReturnShipmentStatusInLMS(entry.getKey(), ShipmentStatus.ON_HOLD_WITH_RETURN_PROCESSING_CENTER.toString(), 15));
				log.info("Marked return "+entry.getKey()+" - ON_HOLD_WITH_RETURN_PROCESSING_CENTER ");
				
			}else if(entry.getValue().toString().equals(ItemQCStatus.SHORTAGE.toString())) {
				Assert.assertTrue(validateReturnShipmentStatusInLMS(entry.getKey(), ShipmentStatus.RETURN_CREATED.toString(), 15));
				log.info("Marked return "+entry.getKey()+" - Shortage, hence status is RETURN_CREATED ");
				
			}else {
				Assert.assertTrue(validateReturnShipmentStatusInLMS(entry.getKey(), ShipmentStatus.RETURN_SUCCESSFUL.toString(), 15));
				log.info("Marked return "+returnId+" - Return Successful");
				
			}
		}
		
		if(status.toString().equals( ItemQCStatus.ON_HOLD.toString())) {
			Assert.assertTrue(validateReturnShipmentStatusInLMS(returnId, ShipmentStatus.ON_HOLD_WITH_RETURN_PROCESSING_CENTER.toString(), 15));
			log.info("Marked return "+returnId+" - ON_HOLD_WITH_RETURN_PROCESSING_CENTER ");
			
			
		}else if(status.toString().equals( ItemQCStatus.SHORTAGE.toString())) {
			Assert.assertTrue(validateReturnShipmentStatusInLMS(returnId, ShipmentStatus.RETURN_CREATED.toString(), 15));
			log.info("Marked return "+returnId+" - Shortage, hence status is RETURN_CREATED ");
			
		}else {
			Assert.assertTrue(validateReturnShipmentStatusInLMS(returnId, ShipmentStatus.RETURN_SUCCESSFUL.toString(), 15));
			log.info("Marked return "+returnId+" - Return Successful");
			
		}
		Assert.assertTrue(validatePickupShipmentStatusInLMS(returnId,ShipmentStatus.PICKUP_SUCCESSFUL.toString(),10));
		log.info("Marked pickup "+pickupId+" - Pickup Successful");
		
	}
	public void  mlOpenBoxQC(String returnId,ShipmentUpdateEvent event,String trackingNo) throws IOException {
		ShipmentUpdate.ShipmentUpdateBuilder shipmentUpdateBuilder = new ShipmentUpdate.ShipmentUpdateBuilder(
				returnId, event);
		shipmentUpdateBuilder.courierCodeAndTrackingNumber("ML",trackingNo);
		shipmentUpdateBuilder.eventLocation("location");
		shipmentUpdateBuilder.eventTime(new DateTime());
		shipmentUpdateBuilder.shipmentType(ShipmentType.PU);
		shipmentUpdateBuilder.shipmentUpdateActivitySource(ShipmentUpdateActivityTypeSource.LogisticsPortal);
		shipmentUpdateBuilder.userName("User");
		shipmentUpdateBuilder.remarks("remarks");
		shipmentUpdateBuilder.eventLocationPremise(new Premise("5", Premise.PremiseType.DELIVERY_CENTER) );
		DomainShipmentUpdateResponse openBoxPickupQCCompleteUpdate = new DomainShipmentUpdateResponse();
		String payload = APIUtilities.getObjectToJSON(shipmentUpdateBuilder.build() );
		
		Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.UPDATE_OPENBOX_QC_STATUS, null,
				SERVICE_TYPE.LMS_SVC.toString(), HTTPMethods.PUT, payload, Headers.getLmsHeaderJSON());
		
	}
	
	public void PQCP_Approve(String returnId, String destWarehouseId, String deliveryCenterID,
	                                       String tripOrderAssignmentId, String trackingNumber,String status) throws InterruptedException, JAXBException, IOException, JSONException, XMLStreamException, ManagerException {
		markTripPickupSuccessfulQCPending(returnId, destWarehouseId, deliveryCenterID,
				tripOrderAssignmentId);
		mlOpenBoxQC(returnId, ShipmentUpdateEvent.PICKUP_DONE, trackingNumber);
	}
	public void pickupPQCP_CCApproveReject(String returnId, String destWarehouseId, String deliveryCenterID,
	                                 String tripOrderAssignmentId, String trackingNumber,String status) throws InterruptedException, JAXBException, IOException, JSONException, XMLStreamException, ManagerException {
		markTripPickupSuccessfulQCPending(returnId, destWarehouseId, deliveryCenterID,
				tripOrderAssignmentId);
		mlOpenBoxQC(returnId, ShipmentUpdateEvent.PICKUP_ON_HOLD, trackingNumber);
		ExceptionHandler.handleTrue(validatePickupShipmentStatusInLMS(returnId, com.myntra.logistics.platform.domain.ShipmentStatus.ONHOLD_PICKUP_WITH_COURIER.toString(), 10));
		ExceptionHandler.handleTrue(validateReturnShipmentStatusInLMS(returnId, com.myntra.logistics.platform.domain.ShipmentStatus.ONHOLD_RETURN_WITH_COURIER.toString(), 10));
		returnApproveOrReject(returnId, status);
		if (status.equals("APPROVED")) {
			TMSServiceHelper tmsServiceHelper = new TMSServiceHelper();
			long masterBagId = (long) tmsServiceHelper.createNcloseMBforReturn.apply(returnId);
			tmsServiceHelper.processInTMSFromClosedToReturnHub.accept(masterBagId);
			
			
			Assert.assertEquals(lmsServiceHelper.masterBagInScan(masterBagId, EnumSCM.RECEIVED, "Bangalore", 36, "WH")
					.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to inscan masterBag at WH");
			Assert.assertEquals(
					lmsServiceHelper.receiveReturnShipmentFromMasterbag(masterBagId, String.valueOf(returnId)).getStatus().getStatusType().toString(),
					
					EnumSCM.SUCCESS, "Unable to receive shipment in DC");
			
			
			ExceptionHandler.handleTrue(validateReturnShipmentStatusInLMS(returnId, ShipmentStatus.DELIVERED_TO_SELLER.toString(), 10));
			lmsServiceHelper.validateRmsStatusAndRefund(returnId, EnumSCM.RRC, true, Constants.LMS_PATH.sleepTime);
			
		}
		else
			//Reship
		{
			Assert.assertEquals(lmsServiceHelper.mlShipmentUpdate(Long.parseLong("5"), (String)lmsHelper.getReturnsTrackingNumber.apply(returnId), MLShipmentUpdateEvent.RESHIP_TO_CUSTOMER,"Return Reshipped - GLoria", ShipmentType.PU),EnumSCM.SUCCESS);
			
			validateMLShipmentStatus(getPickupTrackingNoOfReturn(returnId),"RESHIPPED_TO_CUSTOMER");
			
		}
	}
	private void markTripPickupSuccessfulQCPending(String returnId, String destWarehouseId,
	                                               String deliveryCenterID, String tripOrderAssignmentId)
			throws IOException, JAXBException, InterruptedException, ManagerException {
		LMSHelper lmsHelper = new LMSHelper();
		LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
		TripOrderAssignmentResponse response = lmsServiceHelper.updatePickupInTrip(Long.parseLong(tripOrderAssignmentId), EnumSCM.PICKUP_SUCCESSFUL_QC_PENDING, EnumSCM.UPDATE);
		Assert.assertEquals(response.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to update orders in Trip.");
		Thread.sleep(Constants.LMS_PATH.sleepTime);
		
		TripOrderAssignmentResponse response1 = lmsServiceHelper.updatePickupInTrip(Long.parseLong(tripOrderAssignmentId), EnumSCM.PICKUP_SUCCESSFUL_QC_PENDING, EnumSCM.TRIP_COMPLETE);
		Assert.assertEquals(response1.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to complete Trip.");
		Thread.sleep(Constants.LMS_PATH.sleepTime);
		ExceptionHandler.handleTrue(validatePickupShipmentStatusInLMS(returnId, ShipmentStatus.PICKUP_DONE.toString(),10));
		ExceptionHandler.handleTrue(validateReturnShipmentStatusInLMS(returnId,ShipmentStatus.PICKUP_DONE.toString(),10));
		rmsServiceHelper.validateReturnStatusInRMS(returnId,ReturnStatus.RL.toString(),10);
	}
	
	public OrderResponse returnApproveOrReject(String returnId, String status) throws IOException, JAXBException,
			InterruptedException, ManagerException {
		Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.APPROVE_REJECT_RETURN, new String[]{"" + returnId, status, "gloria.r"}, SERVICE_TYPE.LMS_SVC.toString(),
				HTTPMethods.POST, null, Headers.getLmsHeaderXML());
		OrderResponse response = (OrderResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(),
				new OrderResponse());
		Assert.assertEquals(response.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to Approve/Reject order");
        if (status.equalsIgnoreCase("APPROVED")) {
	        ExceptionHandler.handleTrue(validateReturnShipmentStatusInLMS(returnId,  ShipmentStatus.RETURN_SUCCESSFUL.toString(),10));
	        ExceptionHandler.handleTrue(validatePickupShipmentStatusInLMS(returnId, ShipmentStatus.PICKUP_SUCCESSFUL.toString(),10));
	        validateMLShipmentStatus(getPickupTrackingNoOfReturn(returnId),"APPROVED_ONHOLD_PICKUP_WITH_DC");
	
	        rmsServiceHelper.validateReturnStatusInRMS(returnId,ReturnStatus.RL.toString(),10);
	      
	        ExceptionHandler.handleTrue(rmsServiceHelper.WaitRefundStatus(returnId,30));
	        LmsServiceHelper lmsServiceHelper= new LmsServiceHelper();
	        //Acknowledge
	        Assert.assertEquals(lmsServiceHelper.mlShipmentUpdate(Long.parseLong("5"), (String)lmsHelper.getReturnsTrackingNumber.apply(returnId), MLShipmentUpdateEvent.ACKNOWLEDGE_APPROVE_ONHOLD_PICKUP_WITH_DC,"RETURN_APPROVED", ShipmentType.PU),EnumSCM.SUCCESS);
	        ExceptionHandler.handleTrue(validatePickupShipmentStatusInLMS(returnId,ShipmentStatus.PICKUP_SUCCESSFUL.toString(),10));
	        
        }else{
	        ExceptionHandler.handleTrue(validateReturnShipmentStatusInLMS(returnId,  com.myntra.logistics.platform.domain.ShipmentStatus.RETURN_REJECTED.toString(),10));
	        Assert.assertTrue(validatePickupShipmentStatusInLMS(returnId, ShipmentStatus.PICKUP_REJECTED.toString(),10));
	        rmsServiceHelper.validateReturnStatusInRMS(returnId,ReturnStatus.RRSH.toString(),10);
	        validateMLShipmentStatus(getPickupTrackingNoOfReturn(returnId),"REJECTED_ONHOLD_PICKUP_WITH_DC");
	
	        ReturnResponse returnResponseRMSObj = rmsServiceHelper.getReturnDetailsNew(String.valueOf(Long.parseLong(returnId)));
	        ExceptionHandler.handleFalse(returnResponseRMSObj.getData().get(0).getReturnRefundDetailsEntry().getRefunded()," REFUNDED");
	
        }
		return response;
	}
	public OrderResponse returnApproveOrRejectClosedBox(String returnId, String status) throws IOException, JAXBException,
			InterruptedException, ManagerException {
		Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.APPROVE_REJECT_RETURN, new String[]{"" + returnId, status, "gloria.r"}, SERVICE_TYPE.LMS_SVC.toString(),
				HTTPMethods.POST, null, Headers.getLmsHeaderXML());
		OrderResponse response = (OrderResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(),
				new OrderResponse());
		Assert.assertEquals(response.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to Approve/Reject order");
		if (status.equalsIgnoreCase("APPROVED")) {
			ExceptionHandler.handleTrue(validateReturnShipmentStatusInLMS(returnId,  ShipmentStatus.APPROVED_ONHOLD_RETURN_WITH_RETURNS_PROCESSING_CENTER.toString(),10));
			ExceptionHandler.handleTrue(validatePickupShipmentStatusInLMS(returnId, ShipmentStatus.PICKUP_SUCCESSFUL.toString(),10));
			Assert.assertTrue(rmsServiceHelper.WaitRefundStatus(returnId,20));
		}else{
			ExceptionHandler.handleTrue(validateReturnShipmentStatusInLMS(returnId,  ShipmentStatus.REJECTED_ONHOLD_RETURN_WITH_RETURNS_PROCESSING_CENTER.toString(),10));
			//TODO : gloria check failing
			Assert.assertTrue(validatePickupShipmentStatusInLMS(returnId, ShipmentStatus.PICKUP_SUCCESSFUL.toString(),10));
			rmsServiceHelper.validateReturnStatusInRMS(returnId,ReturnStatus.RRSH.toString(),10);
			
			ReturnResponse returnResponseRMSObj = rmsServiceHelper.getReturnDetailsNew(String.valueOf(Long.parseLong(returnId)));
			Assert.assertFalse(returnResponseRMSObj.getData().get(0).getReturnRefundDetailsEntry().getRefunded()," REFUNDED");
			
		}
		return response;
	}
	
	public String getPickupTrackingNoOfReturn(String returnId) {
		
		
		Map<String, Object> hm = DBUtilities.exSelectQueryForSingleRecord("select tracking_number from pickup_shipment where id in (select pickup_shipment_id_fk  from `return_pickup_association` where return_shipment_id_fk in (select id from return_shipment where  source_return_id =" + returnId + "))", "myntra_lms");
		String tracking_number = (String) hm.get("tracking_number");
		log.info("Tracking number of the associated pickup for the given return is -  " + tracking_number);
		return tracking_number;
	}
	public List<String> getReturnsInPickup(String pickupId){
		
		List<Map<String, Object>> returnListDB= DBUtilities.exSelectQuery("select source_return_id from return_shipment where id in (select return_shipment_id_fk  from `return_pickup_association` where pickup_shipment_id_fk in (select id from pickup_shipment where id in ("+pickupId+")))","myntra_lms");
		List<String> returnIdList= new ArrayList<>();
		returnListDB.forEach(returnid ->
		{
			returnIdList.add((String) returnid.get("source_return_id"));
			
		});
		return returnIdList;
	}
	private String getPickupIdOfReturn(String returnId) {
		
		
		Map<String, Object> hm = DBUtilities.exSelectQueryForSingleRecord("select id from pickup_shipment where id in (select pickup_shipment_id_fk  from `return_pickup_association` where return_shipment_id_fk in (select id from return_shipment where  source_return_id =" + returnId + "))", "myntra_lms");
		Long pickupId = (long) hm.get("id");
		log.info("Pickup Id of the associated pickup for the given return is -  " + pickupId);
		return String.valueOf(pickupId);
	}
	public String generateClosedBoxStatusCSV(String tracking_number, String courier_code, String shipment_type, String activity_type1, String activity_sub_type1) throws IOException {
		log.info("Activity Time is : " + new SimpleDateFormat("dd-MM-yyyy hh:mm:ss").format(new Date()));
		
		String time = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
		java.sql.Date currentTimestamp = new java.sql.Date(Calendar.getInstance().getTimeInMillis());
		String uploadFileHeader = "Tracking Number,Courier Code,Shipment Type,Activity Type,Activity Sub Type,Activity Date,Remarks,Location";
		
		File file = new File("./Data/lms/ClosedBoxUpload" + currentTimestamp + Math.random() + ".csv");
		if (file.exists()) {
			file.delete();
		}
		FileWriter writer = new FileWriter(file, true);
		BufferedWriter bufferedWriter = new BufferedWriter(writer);
		bufferedWriter.write(uploadFileHeader);
		bufferedWriter.newLine();
		bufferedWriter.write(tracking_number + "," + courier_code + "," + shipment_type + "," + activity_type1 + "," + activity_sub_type1 + "," + time + "," + "Test Gloria," + "System");
		bufferedWriter.newLine();
		
		bufferedWriter.close();
		log.info("CLosed Box Upload file - " + file.getName() + " is created in Folder :" + file.getPath());
		return file.getPath();
		
	}
	
	public void manifestClosedBoxPickups(String returnId) throws IOException, JAXBException, InterruptedException,
			ManagerException {
		Thread.sleep(Constants.LMS_PATH.sleepTime);
		Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.CLOSED_BOX_MANIFEST, null, SERVICE_TYPE.LMS_SVC.toString(), HTTPMethods.POST, null, Headers.getLmsHeaderJSON());
		while (!isPickupManifested(returnId)) {
			service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.CLOSED_BOX_MANIFEST, null, SERVICE_TYPE.LMS_SVC.toString(), HTTPMethods.POST, null, Headers.getLmsHeaderJSON());
			
		}
		com.myntra.lms.client.domain.response.PickupResponse pickupResponse = (com.myntra.lms.client.domain.response.PickupResponse) APIUtilities.getJsontoObjectUsingFasterXML(service.getResponseBody(),
				new com.myntra.lms.client.domain.response.PickupResponse());
		
		Assert.assertTrue(pickupResponse.getStatus().getStatusType().equals(StatusResponse.Type.SUCCESS));
		//Verify order_tracking courier creation status = APPROVED
		//validateMLShipmentStatus(pickupResponse.getDomainPickupShipments().get(0).getTrackingNumber().toString(),com.myntra.ml.domain.MLDeliveryShipmentStatus)
	}
	
	public void manifestClosedBoxPickups() throws IOException, JAXBException, InterruptedException,
			ManagerException {
		int cnt=0;
		int no_of_loops=5;
		Svc service=null;
		while(cnt<no_of_loops) {
			
			service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.CLOSED_BOX_MANIFEST, null, SERVICE_TYPE.LMS_SVC.toString(), HTTPMethods.POST, null, Headers.getLmsHeaderJSON());
			Thread.sleep(Constants.LMS_PATH.sleepTime);
			
			cnt++;
		}
		PickupResponse pickupResponse = (PickupResponse) APIUtilities.getJsontoObjectUsingFasterXML(service.getResponseBody(),
				new com.myntra.lms.client.domain.response.PickupResponse());
		
		Assert.assertTrue(pickupResponse.getStatus().getStatusType().equals(StatusResponse.Type.SUCCESS));
	}
	
	public boolean verifyClosedBoxConsolidation(String returnID, String returnID2) {
		List<Map<String, Object>> list = DBUtilities.exSelectQuery("select return_pickup_association.`pickup_shipment_id_fk`,return_pickup_association.`return_shipment_id_fk`,return_shipment.`source_return_id` from return_pickup_association join return_shipment on return_pickup_association.`return_shipment_id_fk`= `return_shipment`.id   where  return_shipment.source_return_id  in (" + returnID + " , " + returnID2 + " )  group by return_pickup_association.`pickup_shipment_id_fk`,return_pickup_association.`return_shipment_id_fk`,return_shipment.`source_return_id` ;", "myntra_lms");
		Map<String, Object> hm = (Map<String, Object>) list.get(0);
		Long pickupId = (Long) hm.get("pickup_shipment_id_fk");
		log.info("Pickup for return " + returnID + " is : " + pickupId);
		
		Map<String, Object> hm2 = (Map<String, Object>) list.get(1);
		Long pickupId2 = (Long) hm2.get("pickup_shipment_id_fk");
		log.info("Pickup for return " + returnID2 + " is : " + pickupId2);
		return pickupId.equals(pickupId2);
	}
	
	
	public boolean validateClosedBoxRmsLmsReturnCreationV2(String returnId, String courier_code) throws IOException, ManagerException,
			JAXBException, InterruptedException, XMLStreamException, JSONException, SQLException {
		
		//Get the return_shipment details as a OrderResponse object, get return from RMS
		OrderResponse returnShipmentLMSObject = getReturnShipmentDetailsLMS(returnId);
		Map<String, Object> returnShipmentLMSDB = DBUtilities.exSelectQueryForSingleRecord("select * from return_shipment where source_return_id = " + returnId, "lms");
		Map<String, Object> returnRMSDB = DBUtilities.exSelectQueryForSingleRecord("select * from returns where id = " + returnId, "rms");
		ReturnResponse returnResponseRMSObj = rmsServiceHelper.getReturnDetailsNew(String.valueOf(Long.parseLong(returnId)));
		
		//Basic validations : return_type=CLosed, courier_code=ML
		ExceptionHandler.handleTrue(returnShipmentLMSObject.getOrders().get(0).getCourierOperator().equals(courier_code), "Courier code assigned to this return is not " + courier_code);
		ExceptionHandler.handleTrue(returnShipmentLMSDB.get("return_type").equals("CLOSED_BOX_PICKUP"), "The return type is not CLOSED_BOX_PICKUP in LMS return");
		ExceptionHandler.handleTrue(returnRMSDB.get("return_mode").equals("CLOSED_BOX_PICKUP"), "The return type is not CLOSED_BOX_PICKUP in RMS return");
		
		//1. Validate if return_shipment  entry is created in LMS  with status "RETURN_CREATED"
		ExceptionHandler.handleTrue(validateReturnShipmentStatusInLMS(returnId, ShipmentStatus.RETURN_CREATED.toString(), 15), "Return-shipment is not created with status RETURN_CREATED in LMS");
		
		//2. Verify the status in RMS initially is LPI
		ExceptionHandler.handleTrue(rmsServiceHelper.validateReturnStatusInRMS(String.valueOf(Long.parseLong(returnId)), ReturnStatus.LPI.toString(), 15), "pickup is not created or not in RRQP status in Returns");
		
		//3. Verify pickup is created with status PICKUP_CREATED
		ExceptionHandler.handleTrue(validatePickupShipmentStatusInLMS(returnId, ShipmentStatus.PICKUP_CREATED.toString(), 15), "Pickup-shipment is not created with status PICKUP_CREATED in LMS");
		
		//4. Verify the right return warehouse id  and return hub code is populated in return_shipment
		String orderId = returnResponseRMSObj.getData().get(0).getOrderId().toString();
		OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
		OrderReleaseEntry orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(omsServiceHelper.getReleaseId(orderId));
		String expectedReturnWHid = String.valueOf(orderReleaseEntry.getWarehouseId());
		
		String expectedReturnHub = lmsHelper.getReturnHubCodeForWarehouse.apply(Long.parseLong(expectedReturnWHid)).toString();
		String actualReturnHub = getReturnWarehouseHub(returnId);
		String actualReturnWHid = getReturnWarehouseId(returnId);
		ExceptionHandler.handleTrue(actualReturnWHid.equals(expectedReturnWHid), "The return warehouse id stamped in return_shipment is not = the return_hub_warehouse configured");
		ExceptionHandler.handleTrue(expectedReturnHub.equals(actualReturnHub), "The return hub stamped in return_shipment is not = the return_hub_warehouse configured");
		
		
		//6. Verify return_shipment value = RMS refund value
//		ExceptionHandler.handleTrue(returnShipmentLMSObject.getOrders().get(0).getItemEntries().get(0).getItemValue().toString().equals(returnResponseRMSObj.getData().get(0).getReturnLineEntries().get(0).getReturnLineRefundDetailsEntry().getRefundAmount().toString()), "LMS Return_shipment shipment value not equal to RMS return refund value");
		
		//Verify return_shipment status = RIT
		ExceptionHandler.handleTrue(returnShipmentLMSObject.getOrders().get(0).getStatus().toString().equals(ShipmentStatus.valueOf("RETURN_CREATED").getShipmentStatusCode().toString()), "LMS return_shipment status is not RIT");
		
		//8. Verify rms (returns table) tracking number = LMS return_shipment tracking number
		log.info("The rms - return tracking number" + returnResponseRMSObj.getData().get(0).getReturnTrackingDetailsEntry());
		ExceptionHandler.handleTrue((returnResponseRMSObj.getData().get(0).getReturnTrackingDetailsEntry().getTrackingNo().toString().equals(returnShipmentLMSObject.getOrders().get(0).getTrackingNumber().toString())), "LMS return_shipment tracking number != RMS return tracking number");
		
		//9. Verify rms(return table) and lms (return_shipment) return entry data
		ExceptionHandler.handleTrue(returnShipmentLMSDB.get("return_type").toString().equals(returnResponseRMSObj.getData().get(0).getReturnMode().toString()), "return_mode/return_type Mismatch in LMS-RMS");
		
		//10 . Verify  LMS-RMS return mode
		ExceptionHandler.handleTrue(returnShipmentLMSDB.get("return_type").toString().equals(returnResponseRMSObj.getData().get(0).getReturnMode().toString()), "LMS RMS return_mode match error");
		
		//11. Verify RMS LMS email id
		ExceptionHandler.handleTrue(returnShipmentLMSObject.getOrders().get(0).getEmail().toString().equals(returnResponseRMSObj.getData().get(0).getEmail().toString()), "email Mismatch in LMS-RMS");
		
		String pickup_shipment_id = getAssociatedPickupForReturn(returnId);
		
		//12. Using the pickup id , get the tracking number stamped in Order tracking table,Verify pickup tracking number and order_tracking's orderId and TrackingNumber match
		String trackingNumber_orderTracking = getOrderTrackingNumber(pickup_shipment_id);
		com.myntra.lms.client.domain.response.PickupResponse pickupResponse = getPickupShipmentDetailsLMS(pickup_shipment_id);
		String trackingNumber_pickupShipment = pickupResponse.getDomainPickupShipments().get(0).getTrackingNumber();
		ExceptionHandler.handleTrue(trackingNumber_orderTracking.toString().equals(trackingNumber_pickupShipment.toString()), "Order Tracking tracking_number !+ pickup_shipment tracking number");
		
		Map<String, Object> orderTrackingLMSDB = DBUtilities.exSelectQueryForSingleRecord("select * from order_tracking where tracking_no = '" + trackingNumber_pickupShipment + "'", "lms");
		
		//13. Verify Order_tracking statuses
		ExceptionHandler.handleTrue(returnShipmentLMSDB.get("courier_code").toString().equals(courier_code), "courier Mismatch in LMS-RMS");
		validateOrderTrackingShipmentStatus(trackingNumber_pickupShipment, ShipmentStatus.PICKUP_CREATED);
		//TODO : why failing for CBT?
		validateOrderTrackingCourierCreationStatus(trackingNumber_pickupShipment,CourierCreationStatus.NOT_INITIATED);
		validateOrderTrackingDeliveryStatus(trackingNumber_pickupShipment, ShipmentStatus.RETURN_CREATED.getOrderTrackingStatusCode().toString());
		ExceptionHandler.handleTrue(orderTrackingLMSDB.get("shipment_status").toString().equals(ShipmentStatus.PICKUP_CREATED.toString()), "order_tracking table shipment_status is not PICKUP_CREATED");
		
		//14. Verify order_Tracking_details
		List<String> activityTypeStatusCombination = new ArrayList<>();
		activityTypeStatusCombination.add("PICKUP_CREATED:PICKUP_CREATED:PICKUP_CREATED");
		activityTypeStatusCombination.add("PICKUP_DETAILS_UPDATED:PICKUP_CREATED:PICKUP_CREATED");
		//validateOrderTrackingDetailsForActivity(trackingNumber_orderTracking,activityTypeStatusCombination);
		return true;
	}
	
	
	public void updateReturnReceiveEvents(String returnId,String courier_code)
			throws JAXBException,  IOException {
		String tracking_number = getPickupTrackingNoOfReturn(returnId);
		
		ShipmentUpdate.ShipmentUpdateBuilder pickupBuilder = new ShipmentUpdate.ShipmentUpdateBuilder(courier_code, tracking_number,
				ShipmentUpdateEvent.RECEIVE);
		pickupBuilder.eventLocation("Myntra");
		pickupBuilder.eventTime(new DateTime());
		pickupBuilder.shipmentType(ShipmentType.PU);
		pickupBuilder.shipmentUpdateActivitySource(ShipmentUpdateActivityTypeSource.LogisticsPlatform);
		pickupBuilder.userName(Context.getContextInfo().getLoginId());
		pickupBuilder.remarks("Pickup Received at Returns Processing Center");
		pickupBuilder.eventLocationPremise(new Premise("5", DELIVERY_CENTER));
		ShipmentUpdate updatePickupStatus = pickupBuilder.build();
		String payload = APIUtilities.getObjectToJSON(updatePickupStatus);
		
		Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.UPDATE_PICKUP_STATUS_EVENT, null,
				SERVICE_TYPE.LMS_SVC.toString(), HTTPMethods.PUT, payload, Headers.getLmsHeaderJSON());
		
		
		/*ShipmentUpdateResponse shipmentUpdateResponse = (ShipmentUpdateResponse) APIUtilities.getJsontoObjectUsingFasterXML(service.getResponseBody(),
				new ShipmentUpdateResponse());*/
		
	}
	
}
